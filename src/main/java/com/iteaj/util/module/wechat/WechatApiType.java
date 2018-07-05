package com.iteaj.util.module.wechat;

/**
 * create time: 2018/4/15
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public enum WechatApiType {
    BasicToken("服务号/订阅号基础Token - 详情：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140183"),
    EnterpriseBasicToken("企业号基础Token - 详情：https://work.weixin.qq.com/api/doc#10013"),
    TemplateMessage("发送模板消息 - 详情：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277"),
    WebAuthorize("服务号网页授权 - 详情：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842"),
    EnterpriseAuthorize("企业号网页授权 - 详情：https://work.weixin.qq.com/api/doc#10028"),
    JsApiConfig("使用微信jsApi的所需的配置 - 详情：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115");
    public String desc;

    WechatApiType(String desc) {
        this.desc = desc;
    }
}
