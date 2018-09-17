package com.iteaj.util.module.wechat.message;

import com.iteaj.util.core.UtilsGlobalFactory;
import com.iteaj.util.module.wechat.WechatConfig;
import com.iteaj.util.module.wechat.WechatTokenManager;

/**
 * Create Date By 2018-04-03
 *
 * @author iteaj
 * @since 1.7
 */
public class WxcTemplateMessage extends WechatConfig<WxaTemplateMessage> {

    private WechatTokenManager tokenManager;

    public WxcTemplateMessage(String appId, String appSecret) {
        this(appId, appSecret, UtilsGlobalFactory.getWechatTokenManager());
    }

    public WxcTemplateMessage(String appId, String appSecret, WechatTokenManager tokenManager) {
        super(appId, appSecret, "https://api.weixin.qq.com/cgi-bin/message/template/send");
        this.tokenManager = tokenManager;
    }

    @Override
    public WxaTemplateMessage buildApi() {
        return new WxaTemplateMessage(this);
    }

    public WechatTokenManager getTokenManager() {
        return tokenManager;
    }

    public WxcTemplateMessage setTokenManager(WechatTokenManager tokenManager) {
        this.tokenManager = tokenManager;
        return this;
    }
}
