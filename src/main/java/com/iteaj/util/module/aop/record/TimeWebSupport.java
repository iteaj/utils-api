package com.iteaj.util.module.aop.record;

import com.iteaj.util.CommonUtils;
import com.iteaj.util.module.aop.WeaveAction;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Create Date By 2018-03-27
 *  提供对web的支持
 * @author iteaj
 * @since 1.7
 */
public class TimeWebSupport extends TimeRecord {

    private static String MARK = "?";
    private static String AJAX_FLAG = "X-Requested-With";

    @Override
    public String generate() {
        WeaveAction action = getAction();
        String uri = null;
        boolean isAjax = false;
        String contextPath = null;

        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        if(requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            String queryString = CommonUtils.isBlank(request.getQueryString()) ? "" : MARK+request.getQueryString();
            uri = request.getRequestURI()+queryString;

            contextPath = request.getContextPath();

            //是否是ajax请求
            isAjax = request.getHeader(AJAX_FLAG) != null;

        }

        StringBuilder sb = new StringBuilder("{");
        sb.append("\"time(ms)\":").append(getTime()).append(',')
                .append("\"uri\":\"").append(uri==null ? "" : uri).append("\",")
                .append("\"ajax\":").append(isAjax).append(",")
                .append("\"contextPath\":\"").append(contextPath).append("\",")
                .append("\"id\":\"").append(getId()).append("\",")
                .append("\"desc\":\"").append(getDesc()).append("\",")
                .append("\"class\":\"").append(action.getTarget().getName()).append("\",")
                .append("\"method\":\"").append(action.getMethod().getName()).append("\",")
                .append("\"dateTime\":\"").append(getDate()).append("\"")
                .append("}");

        return sb.toString();
    }
}
