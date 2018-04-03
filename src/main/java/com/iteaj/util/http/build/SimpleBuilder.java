package com.iteaj.util.http.build;

import com.iteaj.util.CommonUtils;
import com.iteaj.util.UtilsException;
import com.iteaj.util.UtilsType;
import com.iteaj.util.http.AbstractBuilder;
import com.iteaj.util.http.ContentType;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * create time: 2018/4/1
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class SimpleBuilder extends AbstractBuilder {

    private List<byte[]> bodys;

    private SimpleBuilder(String url, ContentType type) {
        super(url, type);
        this.bodys = new ArrayList<>();
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

    public SimpleBuilder addBody(String body) {
        this.addBody(body, "UTF-8");
        return this;
    }

    public SimpleBuilder addBody(byte[] body) {
        this.bodys.add(body);
        return this;
    }

    public SimpleBuilder addBody(String body, String charset) throws UtilsException {
        try {
            this.bodys.add(body.getBytes(charset));
        } catch (UnsupportedEncodingException e) {
            throw new UtilsException("http-不支持的编码: "+charset, e, UtilsType.HTTP);
        }

        return this;
    }

    public List<byte[]> getBodys() {
        return bodys;
    }
}
