package com.iteaj.util.module.wechat;

import com.iteaj.util.CommonUtils;
import com.iteaj.util.core.UtilsException;
import com.iteaj.util.core.UtilsType;
import com.iteaj.util.core.task.TimeoutTask;
import com.iteaj.util.core.task.TimeoutTaskManager;
import com.iteaj.util.module.wechat.basictoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Create Date By 2018-04-13
 *  单应用Token管理实现, 后续会增加分布式应用的Token管理
 *  可以管理多个服务号或者管理多个企业号
 * @author iteaj
 * @since 1.7
 */
public class LocationWechatTokenManager extends WechatTokenManager {

    private int count = 3;
    private double refreshRate = 0.2; //强制刷新必须等待流逝时间的比例
    private double cycleRate = 0.8; //重新获取token的循环比例时间
    private long lastedCreateTime; //服务号最后一次获取token的时间 注意：必须是同一个服务号
    private static LocationWechatTokenManager tokenManager;
    /**
     * 对于企业号：每个应用的Token都是不同的 即：每个应用CorpId对应多个Secret, 每个Secret都有自己的Token <br>
     *     详情：https://work.weixin.qq.com/api/doc#10013/%E7%AC%AC%E4%B8%89%E6%AD%A5%EF%BC%9A%E8%8E%B7%E5%8F%96access_token
     *
     * 对于服务号或者订阅号：AppId和AppSecret标识一个完整的公众号, 所以一个AppId只会有一个AppSecret<br>
     *     详情：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140183
     */
    private Map<String, Map<String, BasicToken>> tokenMap;
    private Logger logger = LoggerFactory.getLogger(getClass());

    protected LocationWechatTokenManager() {
        this.tokenMap = new ConcurrentHashMap<>();
    }


    @Override
    protected BasicToken getBasicToken(WxcBasicToken config, boolean refresh) {
        Map<String, BasicToken> tokenMap = this.tokenMap.get(config.getAppId());
        if(CommonUtils.isNotEmpty(tokenMap)) {
            BasicToken basicToken = tokenMap.get(config.getAppSecret());
            //如果为空说明是第一次获取, 初始化
            if(null == basicToken) {
                //首次初始化必须加锁,
                synchronized (this) {
                    if(null != basicToken) return basicToken;

                    return initTokenManager(config.buildApi());
                }
            }

            //不是强制刷新 那直接返回缓存的数据
            if(!refresh) return basicToken;

            //如果是强制刷新 先验证距上次获取过了多少时间, 如果太短则不刷新 因为频繁刷新会导致上次获取的Token失效
            //否则直接重新调用Api接口, 然后覆盖掉原来的Token
            long pass = System.currentTimeMillis() - lastedCreateTime;
            if(pass >= TimeUnit.SECONDS.toMillis(basicToken.getExpires_in()) * getRefreshRate()) {
                BasicToken invoke = config.buildApi().invoke(WxpBasicToken.instance());
                if(null == invoke) throw new UtilsException("强制刷新Token失败", UtilsType.WECHAT);

                tokenMap.put(config.getAppSecret(), invoke);

                //重置最新的更新时间为当前
                this.lastedCreateTime = System.currentTimeMillis();
                return invoke;
            }
            return basicToken;
        } else {
            return initTokenManager(config.buildApi());
        }
    }

    /**
     * 获取企业号的Token
     * 注意：由于企业号在过期时间内返回的Token都是同一个, 所以有强制刷新的动作直接刷新
     * @param config
     * @param refresh
     * @return
     */
    @Override
    protected BasicToken getBasicToken(WxcEnterpriseBasicToken config, boolean refresh) {
        Map<String, BasicToken> tokenMap = this.tokenMap.get(config.getAppId());
        if(CommonUtils.isNotEmpty(tokenMap)) {
            BasicToken basicToken = tokenMap.get(config.getAppSecret());

            //如果为空说明是第一次获取, 初始化
            if(null == basicToken) return initTokenManager(config.buildApi());

            //不是强制刷新 那直接返回缓存的数据
            if(!refresh) return basicToken;

            //如果是强制刷新则直接重新调用Api接口, 然后覆盖掉原来的Token
            basicToken = config.buildApi().invoke(WxpBasicToken.instance());
            tokenMap.put(config.getAppSecret(), basicToken);

            return basicToken;
        } else {
            return initTokenManager(config.buildApi());
        }
    }

    public static LocationWechatTokenManager instance() {
        if(null != tokenManager) return tokenManager;

        synchronized (LocationWechatTokenManager.class) {
            if(null != tokenManager) return tokenManager;

            return tokenManager = new LocationWechatTokenManager();
        }
    }

    protected BasicToken initTokenManager(WechatBasicTokenApi basicTokenApi) {
        int count = this.count;
        BasicToken basicToken;
        do {
            count --;
            basicToken = basicTokenApi.invoke(null);
            if(count <= 0) {
                logger.error("类别：微信Token管理 - 动作：获取Token - 描述：获取微信token失败 - 失败信息：{}", basicToken);
                return null;
            }
        } while (!basicToken.success());

        Integer expires = basicToken.getExpires_in();
        //计算超时时间
        int cycleTime = new Double(expires * cycleRate()).intValue();

        WxcBasicToken config = basicTokenApi.getApiConfig();
        Map<String, BasicToken> tokenMap = this.tokenMap.get(config.getAppId());
        if(CommonUtils.isNotEmpty(tokenMap)) {
            tokenMap.put(config.getAppSecret(), basicToken);
        } else {
            tokenMap = new ConcurrentHashMap<>();
            tokenMap.put(config.getAppSecret(), basicToken);
            this.tokenMap.put(config.getAppId(), tokenMap);
        }

        TimeoutTaskManager.instance().addTask(
                new TokenTimeoutTask(cycleTime, basicTokenApi));

        if(basicTokenApi.getClass() == WechatBasicTokenApi.class)
            this.lastedCreateTime = System.currentTimeMillis();

        return basicToken;
    }

    /**
     * 重新获取token的循环比例时间
     * @return
     */
    @Override
    public double cycleRate() {
        return cycleRate;
    }

    /**
     * 强制刷新必须等待流逝时间的比例
     * @return
     */
    public double getRefreshRate() {
        return refreshRate;
    }

    public void setRefreshRate(double refreshRate) {
        this.refreshRate = refreshRate;
    }

    class TokenTimeoutTask extends TimeoutTask {

        private WechatBasicTokenApi tokenApi;

        public TokenTimeoutTask(long timeout, WechatBasicTokenApi tokenApi) {
            super(timeout, TimeUnit.SECONDS);
            this.tokenApi = tokenApi;
        }

        @Override
        public void run() {
            initTokenManager(tokenApi);
        }
    }

    public void setCycleRate(double cycleRate) {
        this.cycleRate = cycleRate;
    }

    protected void setCount(int count) {
        this.count = count;
    }
}
