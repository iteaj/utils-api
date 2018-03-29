package com.iteaj.util.module.authorization.http;

import com.iteaj.util.module.authorization.AuthorizeContext;
import com.iteaj.util.module.authorization.AuthorizeResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by iteaj on 2017/3/14.
 */
public abstract class HttpAuthorizeResult implements AuthorizeResult {

    private HttpServletRequest request;
    private HttpServletResponse response;
    protected SessionStorageContext context;

    public HttpAuthorizeResult(SessionStorageContext context) {
        this.context = context;
        this.request = context.getRequest();
        this.response = context.getResponse();
    }

    @Override
    public Object getParam(String key) {
        return context.getParam(key);
    }

    @Override
    public AuthorizeContext getContext() {
        return context;
    }

    public HttpServletRequest getRequest(){
        return request;
    }

    public HttpServletResponse getResponse(){
        return response;
    }
}
