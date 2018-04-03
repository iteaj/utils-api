package com.iteaj.util.wechat;

/**
 * Create Date By 2018-04-03
 *
 * @author iteaj
 * @since 1.7
 */
public class WechatApiBuilder {

    public static  <T extends WechatApi> WechatApi buildApi(WechatConfig<T> config) {
        return config.buildApi();
    }
}
