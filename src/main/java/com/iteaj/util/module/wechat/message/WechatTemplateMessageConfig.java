package com.iteaj.util.module.wechat.message;

import com.iteaj.util.module.wechat.WechatConfig;

/**
 * Create Date By 2018-04-03
 *
 * @author iteaj
 * @since 1.7
 */
public class WechatTemplateMessageConfig extends WechatConfig<TemplateMessageApi> {

    //发送消息网关
    private static String API_GATEWAY = "https://api.weixin.qq.com/cgi-bin/message/template/send";

    public WechatTemplateMessageConfig(String appId, String appSecret) {
        super(appId, appSecret);
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
}
