package com.iteaj.util.core;

/**
 * Create Date By 2018-03-30
 *
 * @author iteaj
 * @since 1.7
 */
public enum UtilsType {

    WEB("javaee相关"),
    AOP(" - 扩展SpringAop"),
    HTTP(" - Http-Api"),
    JSON(" - Json-Api"),
    WECHAT(" - 提供微信公众号Api支持,详情见：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1445241432"),
    OAuth2(" - OAuth2.0支持"),
    ASSERT(" - 断言"),
    TimeoutTask(" - 定时任务管理"),
    Common(" - 常用工具类支持"),
    Digest("签名算法摘要");

    public String desc;

    UtilsType(String desc) {
        this.desc = desc;
    }
}
