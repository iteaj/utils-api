package com.iteaj.util.http;

import com.iteaj.util.UtilsException;
import com.iteaj.util.http.param.UrlBuilder;

import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * create time: 2018/3/27
 *  Http适配器, 用于适配HttpClient、jdk自带{@link java.net.HttpURLConnection} 等
 * @author iteaj
 * @version 1.0
 * @since 1.7
 */
public interface HttpAdapter {

    String RESOVLER = "$$";

    /**
     * 发送Get请求
     * @param url 请求的url
     * @return
     * @throws UtilsException
     */
    byte[] get(URL url) throws UtilsException;

    /**
     *  发送Get请求
     * @param builder   url构造器
     *
     * @return 返回的数据进行utf-8编码
     * @throws UtilsException
     */
    String get(UrlBuilder builder) throws UtilsException;

    /**
     *  发送Get请求
     * @param builder   url 构造器
     * @param charset   返回的数据的编码
     * @return
     * @throws UtilsException
     */
    String get(UrlBuilder builder, String charset) throws UtilsException;

    /**
     * 发送Post请求 并通过占位符解析url
     * @param url   请求的url e.g http://www.iteaj.com?who=$$&doing=$$
     * @param charset   post请求体的编码
     * @param values    填充url的$$占位符 e.g "iteaj","it"
     * @return
     * @throws UtilsException
     */
    byte[] post(URL url, Charset charset, String... values) throws UtilsException;

    /**
     * 发送Post请求 并通过占位符解析url
     * @param url   请求的url e.g http://www.iteaj.com?who=$$&doing=$$
     * @param body  post请求体
     * @param values    填充url的$$占位符 e.g "iteaj","it"
     * @return
     * @throws UtilsException
     */
    byte[] post(String url, byte[] body, String... values) throws UtilsException;

    /**
     * 发送Post请求 并通过占位符解析url
     * @param url   请求的url e.g http://www.iteaj.com?who=$$&doing=$$
     * @param charset   post请求体的编码
     * @param values    填充url的$$占位符 e.g "iteaj","it"
     * @return  返回的参数用Utf-8编码
     * @throws UtilsException
     */
    String post(String url, Charset charset, String... values) throws UtilsException;

    /**
     * 发送Post请求 并通过占位符解析url
     * @param url   请求的url e.g http://www.iteaj.com?who=$$&doing=$$
     * @param files     要发送的文件
     * @param values    填充url的$$占位符 e.g "iteaj","it"
     * @return
     * @throws UtilsException
     */
    byte[] post(String url, File[] files, String... values) throws UtilsException;
}
