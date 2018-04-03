package com.iteaj.util;

import com.iteaj.util.wechat.basictoken.WechatBasicToken;
import com.iteaj.util.wechat.basictoken.WechatBasicTokenConfig;

/**
 * Create Date By 2018-04-03
 *
 * @author iteaj
 * @since 1.7
 */
public class UtilsTest {

    public static void main(String[] args) {
        WechatBasicTokenConfig config = new WechatBasicTokenConfig("", "");
        WechatBasicToken.BasicToken token = UtilsBuilder.wechatApi(config).invoke(null);
    }
}
