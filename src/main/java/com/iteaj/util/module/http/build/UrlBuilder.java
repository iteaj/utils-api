package com.iteaj.util.module.http.build;

import com.iteaj.util.CommonUtils;
import com.iteaj.util.Const;
import com.iteaj.util.core.UtilsException;
import com.iteaj.util.core.UtilsType;
import com.iteaj.util.module.http.AbstractBuilder;
import com.iteaj.util.module.http.ContentType;

/**
 * Create Date By 2018-03-30
 *  url构建器, 只有一个私有的构造方法, 不能直接构建此对象
 *  请通过静态方法 {@link #build(String)}创建一个对象
 *  然后通过{@link #addParam(String, String)}增加url参数.
 *
 *  在使用的时候会通过方法{@link #parseUrl()}构建一个完整的url<br>
 *      e.g  http://www.iteaj.com?who=iteaj&doing=it
 * @author iteaj
 * @since 1.7
 */
public class UrlBuilder extends AbstractBuilder {

    public UrlBuilder(String url, String charset) {
        super(url, charset);
    }

    /**
     * 构建一个实例对象通过默认的编码(UTF-8)
     * @param url   必填
     * @return
     */
    public static UrlBuilder build(String url) throws UtilsException {
        if(!CommonUtils.isNotBlank(url))
            throw new UtilsException("未指定请求URL", UtilsType.HTTP);

        return new UrlBuilder(url, Const.UTF_8.name());
    }

    /**
     * 构建一个实例对象通过指定的编码
     * @param url   必填
     * @return
     */
    public static UrlBuilder build(String url, String charset) throws UtilsException {
        if(!CommonUtils.isNotBlank(url))
            throw new UtilsException("未指定请求URL", UtilsType.HTTP);

        return new UrlBuilder(url, charset);
    }

    @Override
    public UrlBuilder addParam(String name, String value) {
        return (UrlBuilder) super.addParam(name, value);
    }

    @Override
    public UrlBuilder addHead(String name, String value) {
        return (UrlBuilder) super.addHead(name, value);
    }
}
