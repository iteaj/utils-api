package com.iteaj.util.http;

import com.iteaj.util.CommonUtils;
import com.iteaj.util.UtilsException;
import com.iteaj.util.UtilsType;
import com.iteaj.util.http.build.EntityBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * create time: 2018/3/31
 *
 * @author iteaj
 * @version 1.0
 * @since 1.7
 */
public abstract class AbstractBuilder {

    protected ContentType type;
    protected StringBuilder url;
    protected List<UrlParam> params;

    public AbstractBuilder(String url, ContentType type) {
        this.type = type;
        this.params = new ArrayList<>();
        this.url = new StringBuilder(url);
    }

    /**
     * 增加url参数 key-value形式
     *  属查询参数, 这些参数会拼接到Url后面
     *  注意：不进行Url编码, 如需编码, 请用{@link java.net.URLEncoder}编码
     * @param name
     * @param value
     * @return
     */
    public AbstractBuilder addParam(String name, String value) {
        params.add(new UrlParam(name, value));
        return this;
    }

    /**
     * 解析Url<br>
     * @see #params 将参数合并到Url后面 e.g. http://www.iteaj.com?who=iteaj&doing=it
     *
     * @return
     */
    public String parseUrl() {
        boolean isParam = CommonUtils.isNotEmpty(params);

        int indexOf = url.indexOf("?");
        char charAt = url.charAt(url.length() - 1);
        if(-1 != indexOf && isParam) {
            if(charAt != '&' && charAt != '?') url.append('&');
        } else if(-1 == indexOf && isParam) {
            url.append('?');
        }

        if(isParam) {
            for(UrlParam item : params) {
                url.append(item.getName()).append('=')
                        .append(item.getValue()).append('&');
            }

            url.deleteCharAt(url.length()-1);
        }
        return url.toString();
    }

    public List<UrlParam> getParams() {
        return params;
    }

    public StringBuilder getUrl() {
        return url;
    }

    public ContentType getType() {
        return type;
    }

    protected class UrlParam {
        private String name;
        private String value;
        private String charset;

        public UrlParam(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public UrlParam(String name, String value, String charset) {
            this.name = name;
            this.value = value;
            this.charset = charset;
            if(!CommonUtils.isNotBlank(name))
                throw new UtilsException("Http-Url参数名错误", UtilsType.HTTP);

            if(null == value) this.value="";
        }

        public String getName() {
            return name;
        }

        public UrlParam setName(String name) {
            this.name = name;
            return this;
        }

        public String getValue() {
            return value;
        }

        public UrlParam setValue(String value) {
            this.value = value;
            return this;
        }

        public String getCharset() {
            return charset;
        }

        public void setCharset(String charset) {
            this.charset = charset;
        }
    }
}
