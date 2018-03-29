package com.iteaj.util.module.authorization;

/**
 * <p>整个授权类型执行过程中的上下文参数</p>
 *      注：不同阶段有些参数可能不一样
 * 注意：此对象非线程安全,不能用全局对象
 * Create Date By 2017-03-08
 * @author iteaj
 * @since 1.7
 */
public interface AuthorizeContext {

    /**
     * 初始化类型上下文
     * @param type
     */
    void init(AuthorizationType type, AuthorizeActionAbstract action);

    /**
     * 释放上下文
     */
    void release();

    /**
     * 获取将要被执行的阶段
     * @return
     */
    AuthorizePhase getTickPhase();

    /**
     * 返回指定的请求参数
     * @param name
     * @return
     */
    Object getParam(String name);

    /**
     * 增加一个参数
     * @param name
     * @param value
     * @return
     */
    AuthorizeContext addParam(String name, Object value);

    /**
     * <p>返回授权类型</p>
     * @return
     */
    AuthorizationType getType();

    /**
     * <p>返回授权动作</p>
     * @return
     */
    AuthorizeActionAbstract getAuthorizeAction();
}
