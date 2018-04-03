package com.iteaj.util;

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
    WECHAT("提供微信Api支持"),
    ASSERT("断言"),
    Common("其他常用的工具类支持");

    public String desc;

    UtilsType(String desc) {
        this.desc = desc;
    }
}
