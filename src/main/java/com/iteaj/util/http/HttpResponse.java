package com.iteaj.util.http;

import java.io.Closeable;

/**
 * create time: 2018/4/1
 *  Http服务器的响应内容
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public interface HttpResponse extends Closeable {

    /**
     * 返回状态码
     * @return
     */
    int getStatus();

    /**
     * 返回是否响应 200
     * @return
     */
    boolean success();

    /**
     * 返回状态消息
     * @return
     */
    String getMessage();

    /**
     * 返回服务器的响应内容
     * @return
     */
    byte[] getContent();

    /**
     * 对响应的内容解析charset编码
     * @param charset
     * @return
     */
    String getContent(String charset);

    /**
     * 返回指定的响应头
     * @param name
     * @return
     */
    String getHeader(String name);

    /**
     * 返回内容编码
     * @return
     */
    String getContentEncoding();
}
