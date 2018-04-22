package com.iteaj.util.core;

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
import com.iteaj.util.module.wechat.LocationWechatTokenManager;
import com.iteaj.util.module.wechat.WechatTokenManager;

/**
 * create time: 2018/4/7
 *  项目里面一些常用的工具的默认配置管理
 *  注意：这些配置信息请在项目启动的时候进行配置好, 在项目运行的时候最好不要更改
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class UtilsGlobalDefaultFactory {

    private static Object lock = new Object();
    private static JsonAdapter defaultJsonAdapter;
    private static HttpAdapter defaultHttpAdapter;
    private static WechatTokenManager tokenManager;
    private static SSLContextManager defaultSslManager;
    private static HttpRequestConfig defaultRequestConfig;
    private static AuthorizeStorageManager defaultStorageManager;

    static {
        //初始化默认的Https证书管理
        defaultSslManager = new SSLContextManager();
        //初始化默认的OAuth2上下文存储管理
        defaultStorageManager = new MapStorageManager();
        //初始化默认的微信Token管理
        tokenManager = LocationWechatTokenManager.instance();
        //初始化默认的Http请求配置
        defaultRequestConfig = HttpRequestConfig.getDefault();
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
        if(UtilsGlobalDefaultFactory.defaultHttpAdapter == null)
            UtilsGlobalDefaultFactory.defaultHttpAdapter = defaultHttpAdapter;
    }

    public static AuthorizeStorageManager getDefaultStorageManager() {
        return defaultStorageManager;
    }

    public static void setDefaultStorageManager(AuthorizeStorageManager defaultStorageManager) {
        if(defaultStorageManager.getClass() != MapStorageManager.class)
            UtilsGlobalDefaultFactory.defaultStorageManager = defaultStorageManager;
    }

    public static JsonAdapter getDefaultJsonAdapter() {
        if(defaultJsonAdapter != null)
            return defaultJsonAdapter;

        synchronized (lock) {
            if(defaultJsonAdapter!=null)
                return defaultJsonAdapter;
            try {
                defaultJsonAdapter = new JacksonAdapter();
            } catch (Throwable e) {
                defaultJsonAdapter = new FastJsonAdapter();
            }
            if(defaultJsonAdapter == null)
                throw new UtilsException("无任何Json的实现依赖(Jackson、FastJson)", UtilsType.JSON);

            return defaultJsonAdapter;
        }
    }

    public static void setDefaultJsonAdapter(JsonAdapter defaultJsonAdapter) {
        UtilsGlobalDefaultFactory.defaultJsonAdapter = defaultJsonAdapter;
    }

    public static HttpRequestConfig getDefaultRequestConfig() {
        if(defaultRequestConfig == null)
            return HttpRequestConfig.getDefault();

        return defaultRequestConfig;
    }

    public static void setDefaultRequestConfig(HttpRequestConfig defaultRequestConfig) {
        UtilsGlobalDefaultFactory.defaultRequestConfig = defaultRequestConfig;
    }

    public static SSLContextManager getDefaultSslManager() {
        return defaultSslManager;
    }

    public static void setDefaultSslManager(SSLContextManager defaultSslManager) {
        UtilsGlobalDefaultFactory.defaultSslManager = defaultSslManager;
    }

    public static WechatTokenManager getTokenManager() {
        return tokenManager;
    }

    public static void setTokenManager(WechatTokenManager tokenManager) {
        UtilsGlobalDefaultFactory.tokenManager = tokenManager;
    }
}
