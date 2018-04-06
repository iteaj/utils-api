package com.iteaj.util.module.oauth2;

import java.io.Serializable;

/**
 * <p>整个授权类型执行过程中需要存储的参数</p>
 *
 * 注意：此对象非线程安全,不能用全局对象
 * Create Date By 2017-03-08
 * @author iteaj
 * @since 1.7
 */
public interface AuthorizeContext extends Serializable {

    /**
     * 释放上下文
     */
    void release();

    /**
     * 返回一个Key, 此key用来唯一指定一个上下文
     * @return
     */
    String getContextKey();

    /**
     * 返回指定的请求参数
     * @param name
     * @return
     */
    Object getContextParam(String name);

    /**
     * 增加一个参数
     * @param name
     * @param value
     * @return
     */
    AuthorizeContext addContextParam(String name, Object value);

    /**
     * 移除一个参数
     * @param name
     * @return
     */
    AuthorizeContext removeContextParam(String name);

}
