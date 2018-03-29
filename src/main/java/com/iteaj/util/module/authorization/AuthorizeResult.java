package com.iteaj.util.module.authorization;

/**
 * Created by iteaj on 2017/3/14.
 */
public interface AuthorizeResult {

    Object getParam(String key);

    AuthorizeContext getContext();
}
