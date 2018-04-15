package com.iteaj.util.module.http.adapter;

import com.iteaj.util.AssertUtils;
import com.iteaj.util.CommonUtils;
import com.iteaj.util.core.UtilsException;
import com.iteaj.util.core.UtilsGlobalDefaultFactory;
import com.iteaj.util.core.UtilsType;
import com.iteaj.util.module.http.AbstractBuilder;
import com.iteaj.util.module.http.HttpAdapter;
import com.iteaj.util.module.http.HttpHead;
import com.iteaj.util.module.http.HttpRequestConfig;
import com.iteaj.util.module.http.build.EntityBuilder;
import com.iteaj.util.module.http.build.TextBuilder;
import com.iteaj.util.module.http.build.UrlBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * create time: 2018/4/1
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class HttpClientAdapter implements HttpAdapter<HttpClientResponse> {

    private static RequestConfig config;
    private static Object syncLock = new Object();
    private static CloseableHttpClient httpClient;
    private static HttpClientAdapter httpClientAdapter;
    private static PoolingHttpClientConnectionManager connectionManager;

    protected HttpClientAdapter() {

    }

    public static HttpClientAdapter instance() {
        if(null != httpClientAdapter)
            return httpClientAdapter;

        synchronized (syncLock) {
            if(null != httpClientAdapter)
                return httpClientAdapter;

            HttpRequestConfig config = UtilsGlobalDefaultFactory.getDefaultRequestConfig();
            connectionManager = new PoolingHttpClientConnectionManager();
            /*连接池容纳的最大连接数*/
            connectionManager.setMaxTotal(config.getMaxTotal());
            /*设置每个路由的最大连接数*/
            connectionManager.setDefaultMaxPerRoute(config.getDefaultMaxPerRoute());

            HttpClientAdapter.config = RequestConfig.custom()
                    /*从服务端读取数据的等待时间*/
                    .setSocketTimeout(config.getReadTimeout())
                    /*连接服务端等待时间*/
                    .setConnectTimeout(config.getConnectTimeout())
                    /*是否允许重定向*/
                    .setRedirectsEnabled(config.isFollowRedirects())
                    /*从连接池获取连接等待时间*/
                    .setConnectionRequestTimeout(config.getConnectionRequestTimeout())
                    .build();

            httpClient = HttpClients.custom().setConnectionManager(connectionManager)
                    .setDefaultRequestConfig(HttpClientAdapter.config)
                    .build();

            httpClientAdapter = new HttpClientAdapter();
            return httpClientAdapter;
        }

    }

    @Override
    public HttpClientResponse get(UrlBuilder builder) throws UtilsException {
        AssertUtils.isTrue(null != builder, "未指定参数构建器", UtilsType.HTTP);
        try {
            HttpGet get = new HttpGet(builder.parseUrl());

            setHeader(builder, get);
            setRequestConfig(get, builder.getRequestConfig());

            return new HttpClientResponse(httpClient.execute(get));
        } catch (IOException e) {
            throw new UtilsException("发送请求失败", e, UtilsType.HTTP);
        }
    }

    @Override
    public HttpClientResponse post(TextBuilder builder) throws UtilsException {
        AssertUtils.isTrue(null != builder, "未指定参数构建器", UtilsType.HTTP);
        try {
            HttpPost post = new HttpPost(builder.parseUrl());

            setHeader(builder, post);
            setRequestConfig(post, builder.getRequestConfig());

            post.setEntity(new StringEntity(builder.getTestString(), builder.getCharset()));
            return new HttpClientResponse(httpClient.execute(post));
        } catch (IOException e) {
            throw new UtilsException("发送请求失败", e, UtilsType.HTTP);
        }
    }

    private void setRequestConfig(HttpRequestBase post, HttpRequestConfig requestConfig) {
        HttpRequestConfig defaultRequestConfig = UtilsGlobalDefaultFactory.getDefaultRequestConfig();
        if (requestConfig != null && requestConfig != defaultRequestConfig)
            post.setConfig(convertConfig(requestConfig));
    }

    @Override
    public HttpClientResponse post(EntityBuilder builder) throws UtilsException {
        AssertUtils.isTrue(null != builder, "未指定参数构建器", UtilsType.HTTP);
        try {
            HttpPost post = new HttpPost(builder.parseUrl());
            setHeader(builder, post);
            setRequestConfig(post, builder.getRequestConfig());

            HttpEntity entity;
            List<EntityBuilder.EntityParam> entitys = builder.getEntitys();
            if(CommonUtils.isNotEmpty(entitys)) {
                if (builder.getType() == com.iteaj.util.module.http.ContentType.Multipart) {
                    MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
                    for (EntityBuilder.EntityParam body : entitys) {
                        if (body.isFile()) entityBuilder.addBinaryBody(body.getName()
                                , body.getContent(), convertType(body.getType()), body.getValue());
                        else entityBuilder.addBinaryBody(body.getName(), body.getContent());

                    }

                    entity = entityBuilder.build();
                } else if (builder.getType() == com.iteaj.util.module.http.ContentType.UrlEncoded) {
                    List<NameValuePair> urlParam = new ArrayList<>();
                    for (EntityBuilder.EntityParam body : entitys) {
                        urlParam.add(new BasicNameValuePair(body.getName(), body.getValue()));
                    }
                    entity = new UrlEncodedFormEntity(urlParam, builder.getCharset());
                } else {
                    entity = null;
                }

                post.setEntity(entity);
            }
            return new HttpClientResponse(httpClient.execute(post));
        } catch (IOException e) {
            throw new UtilsException("发送请求失败", e, UtilsType.HTTP);
        }
    }

    private void setHeader(AbstractBuilder builder, HttpRequestBase get) {
        Iterator<HttpHead> iterator = builder.iterator();
        while (iterator.hasNext()) {
            HttpHead next = iterator.next();
            get.addHeader(next.getName(), next.getValue());
        }
    }

    ContentType convertType(com.iteaj.util.module.http.ContentType type) {
        switch (type) {
            case OctetStream: return ContentType.APPLICATION_OCTET_STREAM;
            case Multipart: return ContentType.MULTIPART_FORM_DATA;
            case UrlEncoded: return ContentType.APPLICATION_FORM_URLENCODED;
            case Plain: return ContentType.TEXT_PLAIN;
            case Xml: return ContentType.APPLICATION_XML;
            case Json: return ContentType.APPLICATION_JSON;
            case Html: return ContentType.TEXT_HTML;
            default: return ContentType.TEXT_PLAIN;
        }
    }

    RequestConfig convertConfig(HttpRequestConfig config) {
        return RequestConfig.custom()
                .setSocketTimeout(config.getReadTimeout())
                .setConnectionRequestTimeout(config.getConnectionRequestTimeout())
                .setConnectTimeout(config.getConnectTimeout())
                .setRedirectsEnabled(config.isFollowRedirects())
                .build();
    }

}
