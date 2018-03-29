package com.iteaj.util.module.aop.record;

import com.iteaj.util.module.aop.ActionRecord;
import com.iteaj.util.module.aop.RecordType;
import com.iteaj.util.module.aop.WeaveAction;

import java.util.Date;

/**
 * Create Date By 2016/11/9
 *  监控到异常时记录
 * @author iteaj
 * @since 1.7
 */
public class ExceptionRecord implements ActionRecord {

    private RecordType type;
    private Throwable throwable;
    private WeaveAction action;

    public ExceptionRecord() {
        this(null, null);
    }

    public ExceptionRecord(Throwable throwable, WeaveAction action) {
        this.action = action;
        this.throwable = throwable;
        this.type = RecordType.Exception;
    }

    @Override
    public String getId() {
        return type.name();
    }

    @Override
    public String generate() {
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"errMsg\":\"").append(throwable.getMessage()).append("\",")
                .append("\"id\":\"").append(getId()).append("\",")
                .append("\"desc\":\"").append(getDesc()).append("\",")
                .append("\"class\":\"").append(action.getTarget().getName()).append("\",")
                .append("\"method\":\"").append(action.getMethod().getName()).append("\",")
                .append("\"dateTime\":\"").append(getDate()).append("\"")
                .append("}");
        return sb.toString();
    }

    @Override
    public String getDesc() {
        return type.getName();
    }

    @Override
    public Date getDate() {
        return new Date();
    }

    @Override
    public WeaveAction getAction() {
        return action;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public void setAction(WeaveAction action) {
        this.action = action;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("异常时间：").append(getDate()).append("<br />");
        sb.append(throwable.getMessage()).append("<br />");
        for(StackTraceElement item : throwable.getStackTrace()){
            sb.append("at ").append(item.getClassName()).append('.').append(item.getMethodName())
                    .append('(').append(item.getFileName()).append(':').append(item.getLineNumber()).append(')')
                    .append("<br />");
        }
        return sb.toString();
    }
}
