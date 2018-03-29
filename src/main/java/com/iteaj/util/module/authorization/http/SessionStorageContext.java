package com.iteaj.util.module.authorization.http;

import com.iteaj.util.module.authorization.AuthorizationType;
import com.iteaj.util.module.authorization.AuthorizeActionAbstract;
import com.iteaj.util.module.authorization.AuthorizeContext;
import com.iteaj.util.module.authorization.AuthorizePhase;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Create Date By 2017-03-07
 * @author iteaj
 * @since 1.7
 */
public class SessionStorageContext implements AuthorizeContext {

    private AuthorizationType type;
    private AuthorizePhase tickPhase;
    private HttpServletRequest request;
    private Map<String, Object> storage;
    private HttpServletResponse response;
    private AuthorizeActionAbstract authorizeAction;
    public static final String AUTHORIZE_CONTEXT = SessionStorageContext.class.getName();

    public SessionStorageContext(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.storage = new HashMap();
    }

    @Override
    public void init(AuthorizationType type, AuthorizeActionAbstract authorizeAction) {
        HttpSession session = request.getSession();

        this.type = type;
        this.authorizeAction = authorizeAction;
        this.tickPhase = type.getAuthorizePhase(type.getPhaseEntry());

        if(null == tickPhase)
            throw new IllegalStateException(type.getClass().getName()+" 没有指定属性：phaseEntry");

        //如果没有指定在那个阶段执行动作则使用默认阶段
        if(StringUtils.isEmpty(this.authorizeAction.getInvokePhase()))
            this.authorizeAction.setInvokePhase(type.getAsyncPhase());

        //将授权上下文存入session
        session.setAttribute(AUTHORIZE_CONTEXT, this);
    }

    @Override
    public void release() {
        getRequest().getSession()
                .removeAttribute(AUTHORIZE_CONTEXT);
    }

    @Override
    public Object getParam(String name) {
        Object value = storage.get(name);
        if(value == null)
            value = request.getParameter(name);
        return value;
    }

    @Override
    public AuthorizeContext addParam(String name, Object value) {
        storage.put(name, value);
        return this;
    }

    @Override
    public AuthorizationType getType() {
        return type;
    }

    public AuthorizeActionAbstract getAuthorizeAction() {
        return authorizeAction;
    }

    public Map<String, Object> getParams() {
        return storage;
    }

    public AuthorizePhase getTickPhase() {
        //临时阶段要来保存当前将要被执行的阶段
        AuthorizePhase tempPhase = tickPhase;

        if(null == tempPhase) return null;

        //tickPhase将被指向下一阶段
        tickPhase = tempPhase.nextPhase();

        //授权动作阶段如果等于将要被执行的阶段,则下一阶段不执行
        if(authorizeAction.getInvokePhase().equals(tempPhase.phaseAlias()))
            tickPhase = null;

        return tempPhase;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
}
