package com.iteaj.util.module.wechat.jsapi;

import com.iteaj.util.HttpUtils;
import com.iteaj.util.JsonUtils;
import com.iteaj.util.core.ApiReturn;
import com.iteaj.util.core.UtilsException;
import com.iteaj.util.core.UtilsGlobalDefaultFactory;
import com.iteaj.util.core.UtilsType;
import com.iteaj.util.module.http.build.UrlBuilder;
import com.iteaj.util.module.json.Json;
import com.iteaj.util.module.wechat.*;
import com.iteaj.util.module.wechat.basictoken.BasicToken;
import com.iteaj.util.module.wechat.basictoken.WechatConfigBasicToken;

import java.util.Map;

/**
 * create time: 2018/6/17
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class WechatConfigJsApiConfig extends WechatConfig<WechatConfigJsApiConfig.WechatJsApi> {

    private WechatTokenManager tokenManager;
    private String apiGateway = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";

    public WechatConfigJsApiConfig(String appId) {
        super(appId);
        tokenManager = UtilsGlobalDefaultFactory.getTokenManager();
    }

    @Override
    public WechatJsApi buildApi() {
        return new WechatJsApi(this);
    }

    @Override
    public String getApiGateway() {
        return apiGateway;
    }

    @Override
    public void setApiGateway(String gateway) {
        this.apiGateway = gateway;
    }

    public WechatTokenManager getTokenManager() {
        return tokenManager;
    }

    public void setTokenManager(WechatTokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    protected class WechatJsApi extends AbstractWechatApi<WechatConfigJsApiConfig, WechatParamJsApi> {

        public WechatJsApi(WechatConfigJsApiConfig config) {
            super(config);
        }

        @Override
        public WechatApiType getApiType() {
            return WechatApiType.JsApiConfig;
        }

        @Override
        public ApiReturn invoke(WechatParamJsApi param) throws UtilsException {
            BasicToken token = getApiConfig().tokenManager.getToken(
                    new WechatConfigBasicToken(getAppId(), getAppSecret()));

            if(!token.success())
                throw new UtilsException("获取AccessToken失败", UtilsType.WECHAT);

            UrlBuilder urlBuilder = UrlBuilder.build(getApiGateway())
                    .addParam("access_token", token.getAccess_token())
                    .addParam("type", "jsapi");

            String resp = HttpUtils.doGet(urlBuilder, "UTF-8");

            Json json = JsonUtils.buildJson(resp);
            json.getNode("ticket").getValString();
            return null;
        }
    }

    protected static class JsApiTicketManager implements Runnable {

        private Map<String, Map<String, String>> cache;
        public String getTicket() {
            return null;
        }

        @Override
        public void run() {

        }
    }
}
