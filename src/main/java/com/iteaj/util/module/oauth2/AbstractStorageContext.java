package com.iteaj.util.module.oauth2;

import com.iteaj.util.AssertUtil;
import com.iteaj.util.CommonUtils;
import com.iteaj.util.UtilsType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * create time: 2018/4/5
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public abstract class AbstractStorageContext implements AuthorizeContext{
    private String contextKey;
    private AuthorizationType type;
    private AuthorizePhase nextPhase;
    private Map<String, Object> storage;
    private transient HttpServletRequest request;
    private transient HttpServletResponse response;
    private AbstractAuthorizeAction authorizeAction;
    private transient static Object lock = new Object();
    private transient AbstractAuthorizeResult authorizeResult;

    public AbstractStorageContext(HttpServletResponse response) {
        this.response = response;
        this.storage = new HashMap<>();
    }

    @Override
    public String getContextKey() {
        if(CommonUtils.isNotBlank(contextKey))
            return contextKey;

        synchronized (lock) {
            this.contextKey = hashCode()+"-"+System.nanoTime();
        }

        return this.contextKey;
    }

    @Override
    public void release() {
        ContextManagerFactory.getDefaultManager().removeContext(getContextKey());
    }

    protected void initContext(AuthorizationType type) throws Exception {
        this.type = type;
        this.nextPhase = type.getAuthorizePhase(type.getPhaseEntry());

        AssertUtil.isTrue(nextPhase != null
                , "OAuth2 - 找不到要执行的入口阶段：phaseEntry 在类型 "
                        +type.getClass().getName(), UtilsType.OAuth2);

        AssertUtil.isTrue(type.authorizeResult() != null, "", UtilsType.OAuth2);

        //初始化授权结果
        Constructor<? extends AbstractAuthorizeResult> constructor = type
                .authorizeResult().getConstructor(AbstractStorageContext.class);
        this.authorizeResult = constructor.newInstance(this);

        ContextManagerFactory.getDefaultManager().putContext(getContextKey(), this);
    }

    @Override
    public Object getContextParam(String name) {
        return storage.get(name);
    }

    @Override
    public AuthorizeContext addContextParam(String name, Object value) {
        storage.put(name, value);
        return this;
    }

    @Override
    public AuthorizeContext removeContextParam(String name) {
        storage.remove(name);
        return this;
    }

    protected AuthorizationType getType() {
        return type;
    }

    protected AuthorizePhase getNextPhase() {
        //临时阶段要来保存当前将要被执行的阶段
        AuthorizePhase tempPhase = nextPhase;

        if(null == tempPhase) return null;

        //指向下一阶段
        nextPhase = tempPhase.nextPhase();
        /**
         * 授权动作存在,那么就必须验证此动作要在那个阶段被调用
         * 如果执行动作的阶段等于tempPhase, 那么下一个阶段就不执行,所以 nextPhase = null
         * 如果执行动作的阶段不等于tempPhase, 那么继续执行
         */
        if(null != getAuthorizeAction()) {
            String invokePhase = getAuthorizeAction().getInvokePhase();
            if(tempPhase.phaseAlias().equals(invokePhase))
                nextPhase = null;
        }

        return tempPhase;
    }

    /**
     * <p>返回授权动作</p>
     * @return
     */
    protected AbstractAuthorizeAction getAuthorizeAction() {
        return authorizeAction;
    }

    protected void setAuthorizeAction(AbstractAuthorizeAction authorizeAction) {
        this.authorizeAction = authorizeAction;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    protected void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    protected void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public AbstractAuthorizeResult getAuthorizeResult() {
        return authorizeResult;
    }
}
