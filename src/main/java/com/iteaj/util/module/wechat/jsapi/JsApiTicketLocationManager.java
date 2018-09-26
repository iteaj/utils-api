package com.iteaj.util.module.wechat.jsapi;

import com.iteaj.util.module.TokenManager;

import java.util.HashMap;

/**
 * create time: 2018/7/17
 *  获取
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class JsApiTicketLocationManager implements TokenManager<WxcJsApiTicket> {

    private HashMap<String, WxcJsApiTicket.WxrJsApiTicket> cache = new HashMap();
    private static JsApiTicketLocationManager instance;

    private JsApiTicketLocationManager() {}

    public static JsApiTicketLocationManager instance() {
        if(null != instance) return instance;

        synchronized (JsApiTicketLocationManager.class) {
            if(null != instance) return instance;

            instance = new JsApiTicketLocationManager();
            return instance;
        }
    }


    @Override
    public WxcJsApiTicket.WxrJsApiTicket getToken(WxcJsApiTicket param) {
        WxcJsApiTicket.WxrJsApiTicket ticket = cache.get(param.getAppId());
        if(ticket != null && !ticket.isExpires(600)) return ticket;

        synchronized (this) {
            ticket = cache.get(param.getAppId());
            if(ticket != null && !ticket.isExpires(600)) return ticket;

            ticket = param.buildApi().invoke(WxpJsApiTicket.instance);
            if(!ticket.success()) return ticket;

            ticket.setInvokeTime(System.currentTimeMillis());
            cache.put(param.getAppId(), ticket);
            return ticket;
        }

    }

    @Override
    public WxcJsApiTicket.WxrJsApiTicket refresh(WxcJsApiTicket param) {
        this.cache.remove(param.getAppId());
        return getToken(param);
    }

}
