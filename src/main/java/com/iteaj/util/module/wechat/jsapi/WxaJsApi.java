package com.iteaj.util.module.wechat.jsapi;

import com.iteaj.util.core.UtilsException;
import com.iteaj.util.core.UtilsGlobalFactory;
import com.iteaj.util.core.UtilsType;
import com.iteaj.util.module.wechat.AbstractWechatApi;
import com.iteaj.util.module.wechat.WechatApiType;

/**
 * create time: 2018/7/17
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class WxaJsApi extends AbstractWechatApi<WxcJsApi, WxpJsApi> {

    public WxaJsApi(WxcJsApi config) {
        super(config);
    }

    @Override
    public WechatApiType getApiType() {
        return WechatApiType.JsApiConfig;
    }

    @Override
    public WxcJsApi.WxrJsApi invoke(WxpJsApi param) throws UtilsException {
        if(null == param) throw new UtilsException("参数错误 - 获取微信JsApi", UtilsType.WECHAT);

        WxcJsApiTicket wxcJsApiTicket = new WxcJsApiTicket(getApiConfig()
                .getAppId(), getApiConfig().getAppSecret());

        WxcJsApiTicket.WxrJsApiTicket ticket = (WxcJsApiTicket.WxrJsApiTicket)UtilsGlobalFactory
                .getWechatJsTicketManager().getToken(wxcJsApiTicket);

        if(!ticket.success())
            throw new UtilsException("获取微信JsApiTicket失败："+ticket.toString(), UtilsType.WECHAT);

        WxcJsApi.WxrJsApi wxrJsApi = getApiConfig().new WxrJsApi();
        return wxrJsApi.build(param.getUrl(), ticket.getTicket());
    }

}
