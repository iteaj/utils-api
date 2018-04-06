package com.iteaj.util.module.wechat.authhorize;

import com.iteaj.util.module.oauth2.OAuth2ApiConfig;
import com.iteaj.util.module.wechat.WechatApi;
import com.iteaj.util.module.wechat.WechatConfig;

/**
 * create time: 2018/4/6
 *  微信基于OAuth2授权的接口Api配置
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public abstract class AbstractWechatOAuth2ApiConfig<A extends WechatApi>
        extends WechatConfig<A> implements OAuth2ApiConfig {

    public AbstractWechatOAuth2ApiConfig(String appId) {
        super(appId);
    }

    public AbstractWechatOAuth2ApiConfig(String appId, String appSecret) {
        super(appId, appSecret);
    }
}
