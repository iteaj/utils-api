package com.iteaj.util.module.http;

import java.util.Iterator;

/**
 * create time: 2018/4/7
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public interface HttpHeaderBuilder {

    /**
     * 增加一个头信息
     * @param name
     * @param value
     * @return
     */
    HttpHeaderBuilder addHead(String name, String value);

    /**
     * 返回一个头的迭代器
     * @return
     */
    Iterator<HttpHead> iterator();
}
