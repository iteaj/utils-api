package com.iteaj.util.module.http.build;

import com.iteaj.util.CommonUtils;
import com.iteaj.util.Const;
import com.iteaj.util.core.UtilsException;
import com.iteaj.util.core.UtilsGlobalDefaultFactory;
import com.iteaj.util.core.UtilsType;
import com.iteaj.util.module.http.AbstractBuilder;
import com.iteaj.util.module.http.ContentType;

import java.io.UnsupportedEncodingException;

/**
 * create time: 2018/4/1
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class StreamBuilder extends AbstractBuilder {

    private byte[] stream;

    private StreamBuilder(String url, String charset) {
        super(url, charset);
    }

    /**
     *
     * @param url
     * @return
     */
    public static StreamBuilder build(String url) {
        return build(url, Const.UTF_8.name());
    }

    /**
     *
     * @param url
     * @return
     */
    public static StreamBuilder build(String url, String charset) {
        if(!CommonUtils.isNotBlank(url))
            throw new UtilsException("未指定请求URL", UtilsType.HTTP);

        if(CommonUtils.isBlank(charset))
            charset = Const.UTF_8.name();

        return new StreamBuilder(url, charset);
    }

    @Override
    public StreamBuilder addParam(String name, String value) {
        return (StreamBuilder) super.addParam(name, value);
    }

    public void setForPlain(String content) {
        if(CommonUtils.isBlank(content))
            throw new UtilsException("未设置请求内容", UtilsType.HTTP);

        try {
            this.stream = content.getBytes(getCharset());
            this.setType(ContentType.Plain);
        } catch (UnsupportedEncodingException e) {
            throw new UtilsException(e.getMessage(), UtilsType.HTTP);
        }
    }

    public void setForJson(String content) {
        if(CommonUtils.isBlank(content))
            throw new UtilsException("未设置请求内容", UtilsType.HTTP);

        try {
            this.stream = content.getBytes(getCharset());
            this.setType(ContentType.Json);
        } catch (UnsupportedEncodingException e) {
            throw new UtilsException(e.getMessage(), UtilsType.HTTP);
        }
    }

    public void setForHtml(String content) {
        if(CommonUtils.isBlank(content))
            throw new UtilsException("未设置请求内容", UtilsType.HTTP);

        try {
            this.stream = content.getBytes(getCharset());
            this.setType(ContentType.Html);
        } catch (UnsupportedEncodingException e) {
            throw new UtilsException(e.getMessage(), UtilsType.HTTP);
        }
    }

    public void setForXml(String content) {
        if(CommonUtils.isBlank(content))
            throw new UtilsException("未设置请求内容", UtilsType.HTTP);

        try {
            this.stream = content.getBytes(getCharset());
            this.setType(ContentType.Xml);
        } catch (UnsupportedEncodingException e) {
            throw new UtilsException(e.getMessage(), UtilsType.HTTP);
        }
    }

    public void setForStream(byte[] bytes) {
        if(!CommonUtils.isNotEmpty(bytes))
            throw new UtilsException("未设置请求内容", UtilsType.HTTP);

        this.stream = bytes;
        this.setType(ContentType.OctetStream);
    }

    public byte[] getStream() {
        return stream;
    }

    @Override
    public String toString() {
        return new String(stream);
    }
}
