package com.iteaj.util.module.aop.factory;

import com.iteaj.util.module.aop.AbstractWeaveActionFactory;
import com.iteaj.util.module.aop.ActionRecord;
import com.iteaj.util.module.aop.AopExtendUtils;
import com.iteaj.util.module.aop.WeaveAction;
import com.iteaj.util.module.aop.record.ExceptionRecord;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;

/**
 * Create Date By 2016/11/17
 * <p>生成异常监控动作的工厂</p> <br>
 *  @see #getWeaveAction(Method, Class) 用来创建时间监控动作{@link WeaveAction}
 * @author iteaj
 * @since 1.7
 */
public class ExceptionWeaveActionFactory extends AbstractWeaveActionFactory {

    private boolean isDao;
    /*开启业务层异常监控*/
    private boolean isBusiness;
    /*开启控制层异常监控*/
    private boolean isController;

    @Override
    protected WeaveAction getWeaveAction(Method method, Class<?> target) {
        return new WeaveAction<ExceptionRecord>(method, target) {

            private Throwable throwable;

            @Override
            public String getIdentifier() {
                return "Exception";
            }

            @Override
            protected void beforeMethodAction(Object ori, Object... args) {
                /*not do nothing*/
            }

            @Override
            protected void afterMethodAction(Object ori, Object returnVal, Object... args) {
                /*not do nothing*/
            }

            @Override
            public void throwableAction(Throwable throwable, Object ori, Object... args) {
                this.throwable = throwable;
            }

            @Override
            protected ExceptionRecord createRecord() throws IllegalAccessException, InstantiationException {

                if(throwable != null) {

                    ExceptionRecord record = (ExceptionRecord)getRecord().newInstance();
                    record.setAction(this);
                    record.setThrowable(throwable);
                    return record;
                }

                return null;
            }
        };
    }

    @Override
    public boolean isMonitoring(Method method, Class<?> targetClass) {
        if(isDao && AopExtendUtils.isDao(method, targetClass)){

            return true;
        }

        if(isController && AopExtendUtils.isController(method, targetClass)) {

            return true;
        }

        if(isBusiness && AopExtendUtils.isBusiness(method, targetClass)){

            return true;
        }

        return false;
    }

    @Override
    public boolean isMatchingRecord(Class<? extends ActionRecord> clazz) {
        if(null == clazz) return false;

        return ExceptionRecord.class.isAssignableFrom(clazz);

    }

    public boolean isDao() {
        return isDao;
    }

    public void setDao(boolean dao) {
        isDao = dao;
    }

    public boolean isBusiness() {
        return isBusiness;
    }

    public void setBusiness(boolean business) {
        isBusiness = business;
    }

    public boolean isController() {
        return isController;
    }

    public void setController(boolean controller) {
        isController = controller;
    }
}
