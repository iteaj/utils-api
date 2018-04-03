package com.iteaj.util.wechat.message;

import com.iteaj.util.wechat.WechatConfig;

/**
 * Create Date By 2018-04-03
 *
 * @author iteaj
 * @since 1.7
 */
public class WechatTemplateMessageConfig extends WechatConfig<TemplateMessage> {

    //发送消息网关
    private static String API_GATEWAY = "https://api.weixin.qq.com/cgi-bin/message/template/send";

    public WechatTemplateMessageConfig(String appId, String appSecret) {
        super(appId, appSecret);
    }

    @Override
    public TemplateMessage buildApi() {
        return new TemplateMessage(this);
    }

    @Override
    public String getApiGateway() {
        return API_GATEWAY;
    }

    @Override
    public void setApiGateway(String gateway) {
        API_GATEWAY = gateway;
    }
}
