package com.iteaj.util.module.authorization;

/**
 * <p>同步授权的执行动作</p>
 * Created by iteaj on 2017/3/13.
 */
class SyncAuthorizeAction<T extends AuthorizeResult>
        extends AuthorizeActionAbstract<T> {

    private T result;

    @Override
    public void call(T result) {
        this.result = result;
    }

    public T getResult() {
        return result;
    }

}
