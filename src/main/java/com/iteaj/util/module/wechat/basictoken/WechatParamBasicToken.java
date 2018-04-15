package com.iteaj.util.module.wechat.basictoken;

import com.iteaj.util.module.wechat.WechatApiParam;

/**
 * create time: 2018/4/13
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class WechatParamBasicToken implements
        WechatApiParam<BasicToken> {

    private static WechatParamBasicToken basicToken = new WechatParamBasicToken();
    protected WechatParamBasicToken() {}

    public static WechatParamBasicToken instance() {
        return basicToken;
    }
}
