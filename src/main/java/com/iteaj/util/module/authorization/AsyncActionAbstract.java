package com.iteaj.util.module.authorization;

/**
 *  <p>认证阶段的异步动作对象</p>
 * Create Date By 2017-03-06
 * @author iteaj
 * @since 1.7
 */
public abstract class AsyncActionAbstract<T extends AuthorizeResult>
        extends AuthorizeActionAbstract<T> {

    public AsyncActionAbstract() {

    }

    /**
     * 指定调用异步动作的阶段以及
     * @param invokePhase
     */
    public AsyncActionAbstract(String invokePhase) {
        super(invokePhase);
    }

}
