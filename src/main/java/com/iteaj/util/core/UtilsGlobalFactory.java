package com.iteaj.util.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iteaj.util.module.TokenManager;
import com.iteaj.util.module.http.HttpAdapter;
import com.iteaj.util.module.http.HttpRequestConfig;
import com.iteaj.util.module.http.SSLContextManager;
import com.iteaj.util.module.http.adapter.HttpClientAdapter;
import com.iteaj.util.module.http.adapter.JdkHttpAdapter;
import com.iteaj.util.module.json.JsonAdapter;
import com.iteaj.util.module.json.fastjson.FastJsonAdapter;
import com.iteaj.util.module.json.jackson.JacksonAdapter;
import com.iteaj.util.module.oauth2.AuthorizeStorageManager;
import com.iteaj.util.module.oauth2.MapStorageManager;
import com.iteaj.util.module.wechat.basictoken.LocationWechatTokenManager;
import com.iteaj.util.module.wechat.WechatTokenManager;
import com.iteaj.util.module.wechat.jsapi.JsApiTicketLocationManager;

/**
 * create time: 2018/4/7
 *  项目里面一些常用的工具的默认配置管理
 *  注意：这些配置信息请在项目启动的时候进行配置好, 在项目运行的时候最好不要更改
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class UtilsGlobalFactory {

    private static Object lock = new Object();
    private static JsonAdapter defaultJsonAdapter;
    private static HttpAdapter defaultHttpAdapter;
    private static WechatTokenManager wechatTokenManager;
    private static TokenManager wechatJsTicketManager;
    private static SSLContextManager defaultSslManager;
    private static HttpRequestConfig defaultRequestConfig;
    private static AuthorizeStorageManager defaultStorageManager;

    static {
        //初始化默认的Https证书管理
        defaultSslManager = new SSLContextManager();
        //初始化默认的OAuth2上下文存储管理
        defaultStorageManager = new MapStorageManager();
        //初始化默认的微信Token管理
        wechatTokenManager = LocationWechatTokenManager.instance();
        //初始化默认的Http请求配置
        defaultRequestConfig = HttpRequestConfig.getDefault();
        //微信JsApiTicket管理
        wechatJsTicketManager = JsApiTicketLocationManager.instance();
    }

    public static HttpAdapter getDefaultHttpAdapter() {
        if(null != defaultHttpAdapter)
            return defaultHttpAdapter;

        synchronized (lock) {
            if(null != defaultHttpAdapter)
                return defaultHttpAdapter;
            try {
                defaultHttpAdapter = HttpClientAdapter.instance();
            } catch (Throwable e) {
                defaultHttpAdapter = new JdkHttpAdapter();
            }

            return defaultHttpAdapter;
        }
    }

    public static void setDefaultHttpAdapter(HttpAdapter defaultHttpAdapter) {
        if(UtilsGlobalFactory.defaultHttpAdapter == null)
            UtilsGlobalFactory.defaultHttpAdapter = defaultHttpAdapter;
    }

    public static AuthorizeStorageManager getDefaultStorageManager() {
        return defaultStorageManager;
    }

    public static void setDefaultStorageManager(AuthorizeStorageManager defaultStorageManager) {
        if(defaultStorageManager.getClass() != MapStorageManager.class)
            UtilsGlobalFactory.defaultStorageManager = defaultStorageManager;
    }

    public static JsonAdapter getDefaultJsonAdapter() {
        if(defaultJsonAdapter != null)
            return defaultJsonAdapter;

        synchronized (lock) {
            if(defaultJsonAdapter!=null)
                return defaultJsonAdapter;
            try {
                defaultJsonAdapter = new JacksonAdapter(new ObjectMapper());
            } catch (Throwable e) {
                defaultJsonAdapter = new FastJsonAdapter();
            }
            if(defaultJsonAdapter == null)
                throw new UtilsException("无任何Json的实现依赖(Jackson、FastJson)", UtilsType.JSON);

            return defaultJsonAdapter;
        }
    }

    public static void setDefaultJsonAdapter(JsonAdapter defaultJsonAdapter) {
        UtilsGlobalFactory.defaultJsonAdapter = defaultJsonAdapter;
    }

    public static HttpRequestConfig getDefaultRequestConfig() {
        if(defaultRequestConfig == null)
            return HttpRequestConfig.getDefault();

        return defaultRequestConfig;
    }

    public static void setDefaultRequestConfig(HttpRequestConfig defaultRequestConfig) {
        UtilsGlobalFactory.defaultRequestConfig = defaultRequestConfig;
    }

    public static SSLContextManager getDefaultSslManager() {
        return defaultSslManager;
    }

    public static void setDefaultSslManager(SSLContextManager defaultSslManager) {
        UtilsGlobalFactory.defaultSslManager = defaultSslManager;
    }

    public static WechatTokenManager getWechatTokenManager() {
        return wechatTokenManager;
    }

    public static void setWechatTokenManager(WechatTokenManager wechatTokenManager) {
        UtilsGlobalFactory.wechatTokenManager = wechatTokenManager;
    }

    public static TokenManager getWechatJsTicketManager() {
        return wechatJsTicketManager;
    }

    public static void setWechatJsTicketManager(TokenManager wechatJsTicketManager) {
        UtilsGlobalFactory.wechatJsTicketManager = wechatJsTicketManager;
    }
}
