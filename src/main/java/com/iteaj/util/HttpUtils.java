package com.iteaj.util;

import com.iteaj.util.http.HttpAdapter;
import com.iteaj.util.http.HttpResponse;
import com.iteaj.util.http.adapter.HttpClientAdapter;
import com.iteaj.util.http.adapter.JdkHttpAdapter;
import com.iteaj.util.http.build.EntityBuilder;
import com.iteaj.util.http.build.SimpleBuilder;
import com.iteaj.util.http.build.UrlBuilder;

/**
 * create time: 2018/4/1
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public abstract class HttpUtils {

    private static HttpAdapter adapter;

    /**
     * 默认优先使用HttpClient
     */
    static {
        try {
            adapter = new HttpClientAdapter();
        } catch (Throwable e) {
            /*doing nothing*/
        }

        if(null == adapter)
            adapter = new JdkHttpAdapter();
    }

    public static byte[] doGet(UrlBuilder builder) {
        HttpResponse response = adapter.get(builder);
        return response.getContent();
    }

    public static String doGet(UrlBuilder builder, String charset) {
        HttpResponse response = adapter.get(builder);
        return response.getContent(charset);
    }

    public static byte[] doPost(EntityBuilder builder) {
        HttpResponse post = adapter.post(builder);
        return post.getContent();
    }

    public static String doPost(EntityBuilder builder, String charset) {
        HttpResponse post = adapter.post(builder);
        return post.getContent(charset);
    }

    public static byte[] doPost(SimpleBuilder builder) {
        HttpResponse post = adapter.post(builder);
        return post.getContent();
    }

    public static String doPost(SimpleBuilder builder, String charset) {
        HttpResponse post = adapter.post(builder);
        return post.getContent(charset);
    }
}
