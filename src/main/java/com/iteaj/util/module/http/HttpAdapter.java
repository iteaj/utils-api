package com.iteaj.util.module.http;

import com.iteaj.util.core.UtilsException;
import com.iteaj.util.module.http.adapter.HttpClientAdapter;
import com.iteaj.util.module.http.adapter.JdkHttpAdapter;
import com.iteaj.util.module.http.build.MultipartBuilder;
import com.iteaj.util.module.http.build.StreamBuilder;
import com.iteaj.util.module.http.build.UrlBuilder;

import java.io.File;

/**
 * create time: 2018/3/27
 *  Http适配器, 用于适配HttpClient{@link HttpClientAdapter} 、<br>
 *      jdk自带{@link java.net.HttpURLConnection} {@link JdkHttpAdapter}等.
 *      向外提供一套简单易用的统一的Api
 * @author iteaj
 * @version 1.0
 * @since 1.7
 */
public interface HttpAdapter<T extends HttpResponse> {

    /**
     *  使用{@link ContentType#UrlEncoded}发送Get请求
     * @param builder   url构造器
     *
     * @return 返回的数据进行utf-8编码
     * @throws UtilsException
     */
    T get(UrlBuilder builder) throws UtilsException;

    /**
     *使用{@link ContentType#UrlEncoded}发送POST请求
     * @param builder
     * @return
     * @throws UtilsException
     */
    T post(UrlBuilder builder) throws UtilsException;

    /**
     * 简单的实体构建器
     * @param builder 用来构建Http请求的Body内容, <br>
     *                和{@link MultipartBuilder}不同的是写入body的参数只有参数Value没有参数名.
     * @see StreamBuilder#setForPlain(String)
     * @return
     * @throws UtilsException
     */
    T post(StreamBuilder builder) throws UtilsException;

    /**
     * 通过{@link ContentType#Multipart}发送Post请求
     * @param builder  用来构建Http请求的Body内容, 每一个参数都包含参数名和参数值的对应关系.
     *                 可以增加文件{@link MultipartBuilder#addBody(String, File)}、
     *                 普通的参数{@link MultipartBuilder#addBody(String, String)}、
     *                 流参数{@link MultipartBuilder#addBody(String, byte[], String)}
     *
     * @return
     * @throws UtilsException
     */
    T post(MultipartBuilder builder) throws UtilsException;

}
