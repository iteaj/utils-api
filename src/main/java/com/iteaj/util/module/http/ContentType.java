package com.iteaj.util.module.http;

import com.iteaj.util.Const;

import java.nio.charset.Charset;

/**
 * create time: 2018/3/31
 *  http 内容类型
 * @author iteaj
 * @version 1.0
 * @since 1.7
 */
public enum ContentType {
    UrlEncoded("application/x-www-form-urlencoded", "UTF-8")
    ,Json("application/json", "UTF-8")
    ,OctetStream("octet-stream", "ISO_8859_1")
    ,Multipart("multipart/form-data", "ISO_8859_1")
    ,Html("text/html", "UTF-8")
    ,Plain("text/plain", "UTF-8")
    ,Xml("text/xml", "UTF-8");

    public String type;
    public String charset;

    ContentType(String type, String charset) {
        this.type = type;
        this.charset = charset;
    }
}
