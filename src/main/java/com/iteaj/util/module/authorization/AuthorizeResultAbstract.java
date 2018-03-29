package com.iteaj.util.module.authorization;

import com.iteaj.util.module.authorization.http.SessionStorageContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <p>授权结果</p>
 * Create Date By 2017-03-10
 * @author iteaj
 * @since 1.7
 */
public class AuthorizeResultAbstract {

    private SessionStorageContext delegation;

    public AuthorizeResultAbstract(SessionStorageContext context) {
        this.delegation = context;
    }

    public Map<String, Object> getResult(){
        return delegation.getParams();
    }

    public Object getParam(String key){
        return delegation.getParam(key);
    }

    public HttpServletRequest getRequest(){
        return delegation.getRequest();
    }

    public HttpServletResponse getResponse(){
        return delegation.getResponse();
    }
}
