package com.iteaj.util.module.http;

import com.iteaj.util.AssertUtils;
import com.iteaj.util.CommonUtils;
import com.iteaj.util.core.UtilsException;
import com.iteaj.util.core.UtilsGlobalDefaultFactory;
import com.iteaj.util.core.UtilsType;

import java.util.*;

/**
 * create time: 2018/3/31
 *
 * @author iteaj
 * @version 1.0
 * @since 1.7
 */
public abstract class AbstractBuilder implements HttpHeaderBuilder{

    private String charset; //字符编码 在Content-Type:application/x-www-form-urlencoded 才生效
    protected ContentType type;
    protected StringBuilder url;
    protected List<UrlParam> params;
    protected Map<String, HttpHead> headers;
    protected HttpRequestConfig requestConfig;

    private static Map<String, HttpHead> defaultHead;
    static {
        defaultHead = new HashMap<>();
        defaultHead.put("Accept", new HttpHead("Accept", "*/*"));
        defaultHead.put("Connection", new HttpHead("Connection", "Keep-Alive"));
        defaultHead.put("Cache-Control", new HttpHead("Cache-Control", "no-cache"));
        defaultHead.put("Accept-Encoding", new HttpHead("Accept-Encoding", "gzip, deflate"));
    }

    public AbstractBuilder(String url, String charset) {
        this(url, charset, ContentType.UrlEncoded
                , UtilsGlobalDefaultFactory.getDefaultRequestConfig());
    }

    public AbstractBuilder(String url, ContentType type) {
        this(url, type.charset, type
                , UtilsGlobalDefaultFactory.getDefaultRequestConfig());
    }

    public AbstractBuilder(String url, String charset
            , ContentType type, HttpRequestConfig requestConfig) {
        this.type = type;
        this.charset = charset;
        this.params = new ArrayList<>();
        this.url = new StringBuilder(url);
        this.requestConfig = requestConfig;
        this.headers = new HashMap<>(defaultHead);
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
        AssertUtils.isNotBlank(name, "未指定请求参数名", UtilsType.HTTP);
        params.add(new UrlParam(name, value));
        return this;
    }

    @Override
    public AbstractBuilder addHead(String name, String value) {
        AssertUtils.isNotBlank(name, "未指定请求头名称", UtilsType.HTTP);
        AssertUtils.isNotBlank(value, "未指定请求头值："+name, UtilsType.HTTP);
        headers.put(name, new HttpHead(name, value));
        return this;
    }

    @Override
    public Iterator<HttpHead> iterator() {
        return headers.values().iterator();
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

    public HttpRequestConfig getRequestConfig() {
        return requestConfig;
    }

    public AbstractBuilder setRequestConfig(HttpRequestConfig requestConfig) {
        this.requestConfig = requestConfig;
        return this;
    }

    public String getCharset() {
        return charset;
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
