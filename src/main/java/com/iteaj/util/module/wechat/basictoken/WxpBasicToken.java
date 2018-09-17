package com.iteaj.util.module.wechat.basictoken;

import com.iteaj.util.module.wechat.WechatApiParam;

/**
 * create time: 2018/4/13
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class WxpBasicToken implements
        WechatApiParam<BasicToken> {

    private static WxpBasicToken basicToken = new WxpBasicToken();
    protected WxpBasicToken() {}

    public static WxpBasicToken instance() {
        return basicToken;
    }
}
