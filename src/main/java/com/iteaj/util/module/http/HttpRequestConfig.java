package com.iteaj.util.module.http;

/**
 * create time: 2018/4/7
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class HttpRequestConfig {

    private int readTimeout;
    private boolean UseCaches;
    private int connectTimeout;
    private boolean followRedirects;

    /*httpClient独有配置*/
    private int maxTotal; //连接池最大的连接数
    private int defaultMaxPerRoute; //设置每个路由的最大连接数
    private int connectionRequestTimeout; //从连接池获取连接等待时间
    private static HttpRequestConfig defaultConfig;

    static {
        defaultConfig = new HttpRequestConfig();
        defaultConfig.setUseCaches(false);
        defaultConfig.setReadTimeout(30000);
        defaultConfig.setFollowRedirects(true);
        defaultConfig.setConnectTimeout(30000);

        defaultConfig.setMaxTotal(200);
        defaultConfig.setDefaultMaxPerRoute(10);
        defaultConfig.setConnectionRequestTimeout(300);
    }

    public static HttpRequestConfig getDefault() {
        return defaultConfig;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public boolean isUseCaches() {
        return UseCaches;
    }

    public void setUseCaches(boolean useCaches) {
        UseCaches = useCaches;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public boolean isFollowRedirects() {
        return followRedirects;
    }

    public void setFollowRedirects(boolean followRedirects) {
        this.followRedirects = followRedirects;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getDefaultMaxPerRoute() {
        return defaultMaxPerRoute;
    }

    public void setDefaultMaxPerRoute(int defaultMaxPerRoute) {
        this.defaultMaxPerRoute = defaultMaxPerRoute;
    }

    public int getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    public void setConnectionRequestTimeout(int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }
}
