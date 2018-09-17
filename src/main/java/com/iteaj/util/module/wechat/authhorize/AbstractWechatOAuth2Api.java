package com.iteaj.util.module.wechat.authhorize;

import com.iteaj.util.module.oauth2.OAuth2ApiParam;
import com.iteaj.util.module.oauth2.OAuth2AuthorizeApi;
import com.iteaj.util.module.wechat.WechatApi;

/**
 * create time: 2018/4/6
 *  微信基于OAuth2实现的授权接口Api 主要包括以下两种：<br>
 *      1.微信服务号或认证订阅号的网页授权{@link WxaWebAuthorize}
 *      2.微信企业号的网页授权{@link WxaEnterpriseAuthorize}
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public abstract class AbstractWechatOAuth2Api<C extends AbstractWechatOAuth2ApiConfig, P extends OAuth2ApiParam>
        extends OAuth2AuthorizeApi<P> implements WechatApi<C, P> {

    private C config;

    @Override
    public String desc() {
        return getApiType().desc;
    }

    public AbstractWechatOAuth2Api(C config) {
        this.config = config;
    }

    @Override
    public C getApiConfig() {
        return config;
    }

    @Override
    public void setApiConfig(C config) {
        this.config = config;
    }
}
