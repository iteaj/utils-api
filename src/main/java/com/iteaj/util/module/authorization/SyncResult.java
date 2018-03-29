package com.iteaj.util.module.authorization;

/**
 * Created by iteaj on 2017/3/15.
 */
public class SyncResult implements AuthorizeResult {

    private AuthorizeContext context;

    public SyncResult() {

    }

    public SyncResult(AuthorizeContext context) {
        this.context = context;
    }

    @Override
    public Object getParam(String key) {
        return context.getParam(key);
    }

    @Override
    public AuthorizeContext getContext() {
        return context;
    }

    public void setContext(AuthorizeContext context) {
        this.context = context;
    }
}
