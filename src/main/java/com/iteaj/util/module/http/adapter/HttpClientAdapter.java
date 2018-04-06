package com.iteaj.util.module.http.adapter;

import com.iteaj.util.core.UtilsException;
import com.iteaj.util.module.http.HttpAdapter;
import com.iteaj.util.module.http.build.EntityBuilder;
import com.iteaj.util.module.http.build.SimpleBuilder;
import com.iteaj.util.module.http.build.UrlBuilder;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

/**
 * create time: 2018/4/1
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class HttpClientAdapter implements HttpAdapter<HttpClientResponse> {

    private final static Object syncLock = new Object();
    private static CloseableHttpClient httpClient=null;
    private static RequestConfig config = RequestConfig.custom().build();
    private PoolingHttpClientConnectionManager connectionManager;

    @Override
    public HttpClientResponse get(UrlBuilder builder) throws UtilsException {
        return null;
    }

    @Override
    public HttpClientResponse post(SimpleBuilder builder) throws UtilsException {
        return null;
    }

    @Override
    public HttpClientResponse post(EntityBuilder builder) throws UtilsException {
        return null;
    }

    public CloseableHttpClient getHttpClient(){
        if(httpClient == null){
            synchronized (syncLock){
                if(httpClient == null){
                    CookieStore cookieStore = new BasicCookieStore();
                    httpClient =HttpClients.custom()
                            .setConnectionManager(connectionManager)
                            .setDefaultCookieStore(cookieStore)
                            .setDefaultRequestConfig(config)
                            .build();
                }
            }
        }

        return httpClient;
    }
}
