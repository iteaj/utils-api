package com.iteaj.util.module.wechat;

import com.iteaj.util.AssertUtils;
import com.iteaj.util.core.UtilsException;
import com.iteaj.util.core.UtilsType;
import com.iteaj.util.module.TokenManager;
import com.iteaj.util.module.wechat.basictoken.BasicToken;
import com.iteaj.util.module.wechat.basictoken.WechatConfigEnterpriseBasicToken;
import com.iteaj.util.module.wechat.basictoken.WechatConfigBasicToken;

/**
 * create time: 2018/4/14
 *  微信Token管理
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public abstract class WechatTokenManager implements TokenManager<WechatConfigBasicToken> {

    @Override
    public final BasicToken getToken(WechatConfigBasicToken config){
        AssertUtils.isTrue(null != config, "未设置配置信息", UtilsType.WECHAT);

        if(config instanceof WechatConfigBasicToken) {
            return getBasicToken(config, false);
        } else if(config instanceof WechatConfigEnterpriseBasicToken) {
            return getBasicToken((WechatConfigEnterpriseBasicToken)config, false);
        } else {
            throw new UtilsException("未知的Token配置", UtilsType.WECHAT);
        }
    }

    /**
     * 手动从微信方强制刷新返回一个Token  请注意：此方法只能在微信的任何Api接口返回40001的时候再调用<br>
     *     其他时候请使用{@link #getToken(WechatConfigBasicToken)}
     * @param config
     * @return
     */
    @Override
    public BasicToken refresh(WechatConfigBasicToken config) {
        AssertUtils.isTrue(null != config, "未指定获取Token的配置参数", UtilsType.WECHAT);
        if(config instanceof WechatConfigBasicToken) {
            return getBasicToken(config, true);
        } else if(config instanceof WechatConfigEnterpriseBasicToken) {
            return getBasicToken((WechatConfigEnterpriseBasicToken)config, true);
        } else {
            throw new UtilsException("未知的Token配置", UtilsType.WECHAT);
        }
    }

    /**
     * 获取服务号和订阅号的基础token
     * @param config
     * @return
     */
    protected abstract BasicToken getBasicToken(WechatConfigBasicToken config, boolean refresh);

    /**
     * 获取企业号的token
     * @param config
     * @return
     */
    protected abstract BasicToken getBasicToken(WechatConfigEnterpriseBasicToken config, boolean refresh);
}
