package com.iteaj.util;

import com.iteaj.util.core.UtilsGlobalDefaultFactory;
import com.iteaj.util.module.http.HttpResponse;
import com.iteaj.util.module.http.adapter.JdkHttpAdapter;
import com.iteaj.util.module.http.build.EntityBuilder;
import com.iteaj.util.module.http.build.TextBuilder;
import com.iteaj.util.module.http.build.UrlBuilder;

import java.net.HttpURLConnection;

/**
 * create time: 2018/4/1
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public abstract class HttpUtils {

    /**
     * 发起一个Get请求
     * @param builder  用来构建Url参数, 参数会追加到源Url后面
     * @return
     */
    public static byte[] doGet(UrlBuilder builder) {
        HttpResponse response = UtilsGlobalDefaultFactory
                .getDefaultHttpAdapter().get(builder);
        return response.getContent();
    }

    /**
     * 发起一个Get请求
     * @param builder  用来构建Url参数, 参数会追加到源Url后面
     * @param charset  用来指定服务器响应的数据以什么字符集进行编码
     * @return
     */
    public static String doGet(UrlBuilder builder, String charset) {
        HttpResponse response = UtilsGlobalDefaultFactory
                .getDefaultHttpAdapter().get(builder);
        return response.getContent(charset);
    }

    public static byte[] doPost(EntityBuilder builder) {
        HttpResponse post = UtilsGlobalDefaultFactory
                .getDefaultHttpAdapter().post(builder);
        return post.getContent();
    }

    /**
     * 发起一个Post请求
     * 1. 如果Content-Type为{@link com.iteaj.util.module.http.ContentType#UrlEncoded} <br>
     *     写入到Body的内容格式为 name1=value1&name2=value2&...
     * 2. 如果Content-Type为{@link com.iteaj.util.module.http.ContentType#Multipart} 可以用来上传文件 <br>
     *     写入到Body的内容格式比较复杂, 详见{@link JdkHttpAdapter#writeEntityContent(HttpURLConnection, EntityBuilder)}
     * @param builder   用来构建Entity参数,发送时候会写入到Http的Body里面
     * @param charset   用来指定服务器响应的数据以什么字符集进行编码
     * @return
     */
    public static String doPost(EntityBuilder builder, String charset) {
        HttpResponse post = UtilsGlobalDefaultFactory
                .getDefaultHttpAdapter().post(builder);
        return post.getContent(charset);
    }

    public static byte[] doPost(TextBuilder builder) {
        HttpResponse post = UtilsGlobalDefaultFactory
                .getDefaultHttpAdapter().post(builder);
        return post.getContent();
    }

    public static String doPost(TextBuilder builder, String charset) {
        HttpResponse post = UtilsGlobalDefaultFactory
                .getDefaultHttpAdapter().post(builder);
        return post.getContent(charset);
    }
}
