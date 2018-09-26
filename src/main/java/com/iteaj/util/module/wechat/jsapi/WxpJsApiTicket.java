package com.iteaj.util.module.wechat.jsapi;

import com.iteaj.util.module.wechat.WechatApiParam;

/**
 * create time: 2018/7/17
 * 作为{@link com.iteaj.util.module.wechat.jsapi.WxcJsApiTicket.WxaJsApiTicket}使用的参数
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class WxpJsApiTicket implements WechatApiParam<WxcJsApiTicket.WxrJsApiTicket> {
    public static WxpJsApiTicket instance = new WxpJsApiTicket();
}
