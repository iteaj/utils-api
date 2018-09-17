package com.iteaj.util;

import com.iteaj.util.core.UtilsGlobalFactory;
import com.iteaj.util.module.http.ContentType;
import com.iteaj.util.module.http.HttpResponse;
import com.iteaj.util.module.http.adapter.JdkHttpAdapter;
import com.iteaj.util.module.http.build.MultipartBuilder;
import com.iteaj.util.module.http.build.StreamBuilder;
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
    public static HttpResponse doGet(UrlBuilder builder) {
        return UtilsGlobalFactory
                .getDefaultHttpAdapter().get(builder);
    }

    /**
     * 发起一个Get请求
     * @param builder  用来构建Url参数, 参数会追加到源Url后面
     * @param charset  用来指定服务器响应的数据以什么字符集进行编码
     * @return
     */
    public static String doGet(UrlBuilder builder, String charset) {
        HttpResponse response = UtilsGlobalFactory
                .getDefaultHttpAdapter().get(builder);
        return response.getContent(charset);
    }

    /**
     * 使用{@link ContentType#UrlEncoded}发送POST请求
     * @param builder
     * @return
     */
    public static HttpResponse doPost(UrlBuilder builder) {
        return UtilsGlobalFactory
                .getDefaultHttpAdapter().post(builder);
    }

    /**
     * 使用{@link ContentType#UrlEncoded}发送POST请求
     * @param builder
     * @return
     */
    public static String doPost(UrlBuilder builder, String charset) {
        HttpResponse post = UtilsGlobalFactory
                .getDefaultHttpAdapter().post(builder);

        return post.getContent(charset);
    }

    /**
     * 通过{@link ContentType#Multipart}发送Post请求
     * @param builder
     * @return
     */
    public static HttpResponse doPost(MultipartBuilder builder) {
        return UtilsGlobalFactory
                .getDefaultHttpAdapter().post(builder);
    }

    /**
     * 通过{@link ContentType#Multipart}发送Post请求
     * 1. 如果Content-Type为{@link com.iteaj.util.module.http.ContentType#UrlEncoded} <br>
     *     写入到Body的内容格式为 name1=value1&name2=value2&...
     * 2. 如果Content-Type为{@link com.iteaj.util.module.http.ContentType#Multipart} 可以用来上传文件 <br>
     *     写入到Body的内容格式比较复杂, 详见{@link JdkHttpAdapter#writeMultipartContent(HttpURLConnection, MultipartBuilder)}
     * @param builder   用来构建Entity参数,发送时候会写入到Http的Body里面
     * @param charset   用来指定服务器响应的数据以什么字符集进行编码
     * @return
     */
    public static String doPost(MultipartBuilder builder, String charset) {
        HttpResponse post = UtilsGlobalFactory
                .getDefaultHttpAdapter().post(builder);
        return post.getContent(charset);
    }

    public static HttpResponse doPost(StreamBuilder builder) {
        return UtilsGlobalFactory
                .getDefaultHttpAdapter().post(builder);
    }

    public static String doPost(StreamBuilder builder, String charset) {
        HttpResponse post = UtilsGlobalFactory
                .getDefaultHttpAdapter().post(builder);
        return post.getContent(charset);
    }
}
