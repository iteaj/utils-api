package com.iteaj.util;

import com.iteaj.util.core.UtilsException;
import com.iteaj.util.core.UtilsType;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * create time: 2018/7/22
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public final class SpringWebUtils implements ApplicationContextAware {

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes)RequestContextHolder
                .getRequestAttributes()).getRequest();
    }

    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static <T> T getRequestAttr(String name) {
        return (T)getRequest().getAttribute(name);
    }

    public static <T> T getSessionAttr(String name) {
        return (T)getSession().getAttribute(name);
    }

    public static <T> T getContextAttr(String name) {
        return (T)getSession().getServletContext().getAttribute(name);
    }

    public static void setRequestAttr(String name, Object val) {
        getRequest().setAttribute(name, val);
    }

    public static void setSessionAttr(String name, Object val) {
        getSession().setAttribute(name, val);
    }

    public static void setContextAttr(String name, Object val) {
        getSession().getServletContext().setAttribute(name, val);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(!(applicationContext instanceof WebApplicationContext))
            throw new ApplicationContextException(getClass().getSimpleName()+"必须在Web项目中注入使用");
    }
}
