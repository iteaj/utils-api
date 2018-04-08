package com.iteaj.util.module.http.build;

import com.iteaj.util.CommonUtils;
import com.iteaj.util.core.UtilsException;
import com.iteaj.util.core.UtilsManagerFactory;
import com.iteaj.util.core.UtilsType;
import com.iteaj.util.module.http.AbstractBuilder;
import com.iteaj.util.module.http.ContentType;
import com.iteaj.util.module.http.HttpRequestConfig;

/**
 * create time: 2018/4/1
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class TextBuilder extends AbstractBuilder {

    private StringBuilder text;

    private TextBuilder(String url, String charset, ContentType type) {
        super(url, charset, type, UtilsManagerFactory
                .getDefaultRequestConfig());
        this.text = new StringBuilder();
    }

    /**
     *
     * @param url
     * @return
     */
    public static TextBuilder build(String url) {
        return build(url, "UTF-8", ContentType.Plain);
    }

    /**
     *
     * @param url
     * @return
     */
    public static TextBuilder build(String url, String charset, ContentType type) {
        if(!CommonUtils.isNotBlank(url))
            throw new UtilsException("Url错误", UtilsType.HTTP);

        if(type == null)
            throw new UtilsException("请指定 Content-Type", UtilsType.HTTP);

        if(ContentType.Multipart == type || ContentType.UrlEncoded == type)
            throw new UtilsException("不支持Content-Type: "+type.type, UtilsType.HTTP);

        return new TextBuilder(url, charset, type);
    }

    @Override
    public TextBuilder addParam(String name, String value) {
        return (TextBuilder) super.addParam(name, value);
    }

    public TextBuilder addText(String body) {
        this.text.append(body);
        return this;
    }

    public StringBuilder getText() {
        return text;
    }

    public String getTestString() {
        return text.toString();
    }
}
