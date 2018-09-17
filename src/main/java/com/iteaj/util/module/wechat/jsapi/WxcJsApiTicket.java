package com.iteaj.util.module.wechat.jsapi;

import com.iteaj.util.HttpUtils;
import com.iteaj.util.JsonUtils;
import com.iteaj.util.core.UtilsException;
import com.iteaj.util.core.UtilsGlobalFactory;
import com.iteaj.util.core.UtilsType;
import com.iteaj.util.module.http.HttpResponse;
import com.iteaj.util.module.http.build.UrlBuilder;
import com.iteaj.util.module.wechat.*;
import com.iteaj.util.module.wechat.basictoken.BasicToken;
import com.iteaj.util.module.wechat.basictoken.WxcBasicToken;

/**
 * create time: 2018/7/17
 * 获取微信jsapi_ticket 所需要的配置
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class WxcJsApiTicket extends WechatConfig<WxcJsApiTicket.WxaJsApiTicket> {

    public WxcJsApiTicket(String appId, String appSecret) {
        super(appId, appSecret, "https://api.weixin.qq.com/cgi-bin/ticket/getticket");
    }

    @Override
    public WxaJsApiTicket buildApi() {
        return new WxaJsApiTicket(this);
    }

    public class WxaJsApiTicket extends AbstractWechatApi<WxcJsApiTicket, WxpJsApiTicket> {

        public WxaJsApiTicket(WxcJsApiTicket config) {
            super(config);
        }

        @Override
        public WechatApiType getApiType() {
            return WechatApiType.JsApiTicket;
        }

        /**
         * 不会返回Null, 但会抛出异常
         * @param param 调用所需的参数
         * @return
         * @throws UtilsException
         */
        @Override
        public WxrJsApiTicket invoke(WxpJsApiTicket param) throws UtilsException {
            WxcBasicToken basicToken = new WxcBasicToken(
                    getApiConfig().getAppId(), getApiConfig().getAppSecret());

            BasicToken token = UtilsGlobalFactory.getWechatTokenManager().getToken(basicToken);

            if(token == null || !token.success())
                throw new UtilsException("获取AccessToken失败", UtilsType.WECHAT);

            UrlBuilder urlBuilder = UrlBuilder.build(getApiConfig().getApiGateway())
                    .addParam("access_token", token.getAccess_token())
                    .addParam("type", "jsapi");

            HttpResponse response = HttpUtils.doGet(urlBuilder);
            if(!response.success()) throw new UtilsException("获取JsApiTicket失败", UtilsType.WECHAT);
            return JsonUtils.toBean(response.getContent("UTF-8"), WxrJsApiTicket.class);
        }
    }

    /**
     * 作为{@link com.iteaj.util.module.wechat.jsapi.WxcJsApiTicket.WxaJsApiTicket#invoke(WxpJsApiTicket)} 的返回值
     */
    public static class WxrJsApiTicket extends WechatApiReturn {
        private String ticket;
        private int expires_in;

        public String getTicket() {
            return ticket;
        }

        public void setTicket(String ticket) {
            this.ticket = ticket;
        }

        public int getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(int expires_in) {
            this.expires_in = expires_in;
        }
    }
}
