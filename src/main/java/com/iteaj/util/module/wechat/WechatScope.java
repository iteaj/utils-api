package com.iteaj.util.module.wechat;

/**
 * create time: 2018/4/11
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public enum WechatScope {
    /**必须由用户进行手动授权(可用于服务号和企业号)*/
    UserInfo("snsapi_userinfo")
    /**静默授权(可用于服务号和企业号)*/
    ,Base("snsapi_base")
    /**企业用户手动授权(只适用于企业号)*/
    ,PrivateInfo("snsapi_privateinfo");

    public String val;
    WechatScope(String val) {
        this.val = val;
    }
}
