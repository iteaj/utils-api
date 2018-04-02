package com.iteaj.util.http;

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
    UrlEncoded("application/x-www-form-urlencoded", Const.UTF_8)
    ,Json("application/json", Const.UTF_8)
    ,OctetStream("octet-stream", null)
    ,Multipart("multipart/form-data", Const.ISO_8859_1)
    ,Html("text/html", Const.UTF_8)
    ,Plain("test/plain", Const.UTF_8)
    ,Xml("text/xml", Const.UTF_8);

    public String type;
    public Charset charset;

    ContentType(String type, Charset charset) {
        this.type = type;
        this.charset = charset;
    }
}
