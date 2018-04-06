package com.iteaj.util.core;

/**
 * Create Date By 2018-03-30
 *
 * @author iteaj
 * @since 1.7
 */
public enum UtilsType {

    AOP("扩展SpringAop"),
    HTTP("对Http的封装"),
    JSON("对Json的封装"),
    WECHAT(" - 提供微信公众号Api支持,详情见：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1445241432"),
    OAuth2("提供OAuth2.0支持"),
    ASSERT("断言"),
    Common("其他常用的工具类支持");

    public String desc;

    UtilsType(String desc) {
        this.desc = desc;
    }
}
