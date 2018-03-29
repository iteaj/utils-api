package com.iteaj.util.module.authorization;

/**
 * <p>授权动作即：在授权成功后应用要执行的业务逻辑</p>
 * 主要分两种：<br>
 *     1.授权需要异步回调 动作属于异步动作 {@link AsyncActionAbstract}
 *     2.授权是同步的 动作属于同步动作 {@link SyncAuthorizeAction}
 * Created by iteaj on 2017/3/13.
 */
public abstract class AuthorizeActionAbstract<T extends AuthorizeResult> {

    protected String invokePhase;

    public AuthorizeActionAbstract() {

    }

    public AuthorizeActionAbstract(String invokePhase) {
        this.invokePhase = invokePhase;
    }

    /**
     * 返回此动作要在那个阶段被调用
     * @return
     */
    public String getInvokePhase(){

        return invokePhase;
    }

    public void setInvokePhase(String invokePhase) {
        this.invokePhase = invokePhase;
    }

    /**
     * <p>授权动作,一般用作授权之后的业务处理</p>
     * @param result
     */
     public abstract void call(T result);
}
