package com.iteaj.util.module.authorization.type;

/**
 * <p>授权类型</p>
 * Create Date By 2017-03-07
 * @author iteaj
 * @since 1.7
 */
public enum TypeEnum {
    TaoBaoApi("淘宝开放平台api授权认证"),
    WechatUserInfo("微信网页授权"),
    WechatAccessToken("微信普通AccessToken授权"),
    WechatEnterpriseAuthorize("微信企业授权");

    public String description;
    TypeEnum(String description){
        this.description = description;
    }
}
