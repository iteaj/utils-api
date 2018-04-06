package com.iteaj.util.module.oauth2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 *
 * Created by iteaj on 2017/3/13.
 */
public abstract class AbstractAuthorizeAction<T extends AbstractAuthorizeResult> implements Serializable {

    protected String invokePhase;

    public AbstractAuthorizeAction() {

    }

    public AbstractAuthorizeAction(String invokePhase) {
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
