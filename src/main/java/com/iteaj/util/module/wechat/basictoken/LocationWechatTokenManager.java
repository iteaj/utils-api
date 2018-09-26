package com.iteaj.util.module.wechat.basictoken;

import com.iteaj.util.CommonUtils;
import com.iteaj.util.core.task.TimeoutTask;
import com.iteaj.util.module.wechat.WechatTokenManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
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
                    if(null != tokenMap.get(config.getAppSecret()))
                        return basicToken;

                    return initBasisToken(config);
                }
            }

            //不是强制刷新并且还没过期则直接返回
            if(!refresh && !basicToken.isExpires(600)) {
                return basicToken;
            }

            //如果是强制刷新或已经过期
            synchronized (this) {
                BasicToken token = tokenMap.get(config.getAppSecret());
                if(!token.isExpires(600))
                    return token;

                return initBasisToken(config);
            }

        } else {
            synchronized (this) {
                Map<String, BasicToken> basicTokenMap = this.tokenMap.get(config.getAppId());
                if(CommonUtils.isNotEmpty(basicTokenMap) &&
                        null != basicTokenMap.get(config.getAppSecret()))
                    return basicTokenMap.get(config.getAppSecret());

                return initBasisToken(config);
            }
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
        return getBasicToken((WxcBasicToken) config, refresh);
    }

    public static LocationWechatTokenManager instance() {
        if(null != tokenManager) return tokenManager;

        synchronized (LocationWechatTokenManager.class) {
            if(null != tokenManager) return tokenManager;

            return tokenManager = new LocationWechatTokenManager();
        }
    }

    protected BasicToken initBasisToken(WxcBasicToken config) {
        int count = this.count;
        long currentTimeMillis;
        BasicToken basicToken;
        do {
            count --;
            currentTimeMillis = System.currentTimeMillis();
            basicToken = config.buildApi().invoke(WxpBasicToken.instance());
            if(count <= 0) {
                logger.error("类别：微信Api - 动作：获取Token - 描述：获取微信token失败 - 失败信息：{}", basicToken);
                return BasicToken.ErrBasicToken;
            }
        } while (!basicToken.success());

        basicToken.setInvokeTime(currentTimeMillis);
        Map<String, BasicToken> basicTokenMap = this.tokenMap.get(config.getAppId());
        if(CommonUtils.isNotEmpty(basicTokenMap)) {
            basicTokenMap.put(config.getAppSecret(), basicToken);
        }else {
            HashMap<String, BasicToken> token = new HashMap<>();
            token.put(config.getAppSecret(), basicToken);
            this.tokenMap.put(config.getAppId(), token);
        }
        return basicToken;
    }

    protected void setCount(int count) {
        this.count = count;
    }
}
