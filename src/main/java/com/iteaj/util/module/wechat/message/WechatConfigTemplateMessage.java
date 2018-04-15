package com.iteaj.util.module.wechat.message;

import com.iteaj.util.core.UtilsGlobalDefaultFactory;
import com.iteaj.util.module.wechat.WechatConfig;
import com.iteaj.util.module.wechat.WechatTokenManager;

/**
 * Create Date By 2018-04-03
 *
 * @author iteaj
 * @since 1.7
 */
public class WechatConfigTemplateMessage extends WechatConfig<TemplateMessageApi> {

    private WechatTokenManager tokenManager;
    //发送消息网关
    private static String API_GATEWAY = "https://api.weixin.qq.com/cgi-bin/message/template/send";

    public WechatConfigTemplateMessage(String appId, String appSecret) {
        this(appId, appSecret, UtilsGlobalDefaultFactory.getTokenManager());
    }

    public WechatConfigTemplateMessage(String appId, String appSecret, WechatTokenManager tokenManager) {
        super(appId, appSecret);
        this.tokenManager = tokenManager;
    }

    @Override
    public TemplateMessageApi buildApi() {
        return new TemplateMessageApi(this);
    }

    @Override
    public String getApiGateway() {
        return API_GATEWAY;
    }

    @Override
    public void setApiGateway(String gateway) {
        API_GATEWAY = gateway;
    }

    public WechatTokenManager getTokenManager() {
        return tokenManager;
    }

    public WechatConfigTemplateMessage setTokenManager(WechatTokenManager tokenManager) {
        this.tokenManager = tokenManager;
        return this;
    }
}
