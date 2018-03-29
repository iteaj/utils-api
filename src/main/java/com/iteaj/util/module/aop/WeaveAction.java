package com.iteaj.util.module.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Create Date By 2016/10/27
 *  监控动作
 *  T 每一种动作都会产生一种记录,
 * @author iteaj
 * @since 1.7
 */
public abstract class WeaveAction<T extends ActionRecord> {

    private Method method;
    private Class<?> target;
    private T actionRecord;
    protected Logger logger = LoggerFactory.getLogger(getClass());

    public WeaveAction(Method method, Class<?> target) {
        this.method = method;
        this.target = target;
    }

    /**
     * 获取织入动作的标识
     * @return
     */
    public abstract String getIdentifier();

    /**
     * 在织入方法执行前调用
     */
    protected abstract void beforeMethodAction(Object ori, Object... args);

    /**
     * 在织入方法执行后调用
     */
    protected abstract void afterMethodAction(Object ori, Object returnVal, Object... args);

    /**
     * 在织入方法抛出异常时执行后调用
     * @param throwable
     */
    public abstract void throwableAction(Throwable throwable, Object ori, Object... args);

    /**
     *  生成监控记录
     */
    protected final T generateRecord(){
        try {

            return this.actionRecord = createRecord();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    protected abstract T createRecord() throws IllegalAccessException, InstantiationException;

    public ActionRecord getActionRecord() {
        return actionRecord;
    }

    protected void setActionRecord(T actionRecord) {
        this.actionRecord = actionRecord;
    }

    public Class<?> getTarget() {
        return target;
    }

    public Method getMethod() {
        return method;
    }
}
