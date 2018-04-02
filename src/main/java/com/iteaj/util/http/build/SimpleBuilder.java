package com.iteaj.util.http.build;

import com.iteaj.util.CommonUtils;
import com.iteaj.util.UtilsException;
import com.iteaj.util.UtilsType;
import com.iteaj.util.http.AbstractBuilder;
import com.iteaj.util.http.ContentType;

import java.io.UnsupportedEncodingException;

/**
 * create time: 2018/4/1
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class SimpleBuilder extends AbstractBuilder {

    private byte[] body;

    private SimpleBuilder(String url, ContentType type) {
        super(url, type);
    }

    /**
     *
     * @param url
     * @return
     */
    public static SimpleBuilder build(String url) {
        return build(url, ContentType.Plain);
    }

    /**
     *
     * @param url
     * @return
     */
    public static SimpleBuilder build(String url, ContentType type) {
        if(!CommonUtils.isNotBlank(url))
            throw new UtilsException("http-Url错误", UtilsType.HTTP);

        if(type == null)
            throw new UtilsException("http-请指定 Content-Type", UtilsType.HTTP);

        if(ContentType.Multipart == type || ContentType.UrlEncoded == type)
            throw new UtilsException("http-不支持Content-Type: "+type.type, UtilsType.HTTP);

        return new SimpleBuilder(url, type);
    }

    @Override
    public SimpleBuilder addParam(String name, String value) {
        return (SimpleBuilder) super.addParam(name, value);
    }

    public void addBody(String body) {
        this.body = body.getBytes();
    }

    public void addBody(byte[] body) {
        this.body = body;
    }

    public void addBody(String body, String charset) throws UtilsException {
        try {
            this.body = body.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new UtilsException("http-不支持的编码: "+charset, e, UtilsType.HTTP);
        }
    }

    public byte[] getBody() {
        return body;
    }
}
