package com.iteaj.util.module.wechat.jsapi;

import com.iteaj.util.core.UtilsException;
import com.iteaj.util.core.UtilsType;
import com.iteaj.util.core.task.TimeoutTask;
import com.iteaj.util.core.task.TimeoutTaskManager;
import com.iteaj.util.module.TokenManager;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

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
        if(ticket != null) return ticket;

        synchronized (this) {
            ticket = cache.get(param.getAppId());
            if(ticket != null) return ticket;

            ticket = param.buildApi().invoke(null);
            if(ticket == null) throw new UtilsException("获取JsApiTicket失败", UtilsType.WECHAT);

            cache.put(param.getAppId(), ticket);
            Integer timeout = new Double(ticket.getExpires_in() * cycleRate()).intValue();
            TimeoutTaskManager.instance().addTask(new TicketTimeoutTask(timeout, TimeUnit.SECONDS, param));
        }

        return ticket;
    }

    @Override
    public WxcJsApiTicket.WxrJsApiTicket refresh(WxcJsApiTicket param) {
        if(null == param) throw new UtilsException("未指定参数:获取JsApiTicket", UtilsType.WECHAT);

        synchronized (this) {
            WxcJsApiTicket.WxrJsApiTicket apiTicket = param.buildApi().invoke(null);

            cache.put(param.getAppId(), apiTicket);

            Integer timeout = new Double(apiTicket.getExpires_in() * cycleRate()).intValue();
            TimeoutTaskManager.instance().addTask(new TicketTimeoutTask(timeout, TimeUnit.SECONDS, param));

            return apiTicket;
        }
    }

    @Override
    public double cycleRate() {
        return 0.8;
    }

    protected class TicketTimeoutTask extends TimeoutTask {

        private WxcJsApiTicket ticket;

        public TicketTimeoutTask(long timeout, TimeUnit unit, WxcJsApiTicket ticket) {
            super(timeout, unit);
            this.ticket = ticket;
        }

        @Override
        public void run() {
            refresh(ticket);
        }
    }
}
