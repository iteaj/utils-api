package com.iteaj.util.module.aop.factory.time;

import com.iteaj.util.module.aop.AbstractWeaveActionFactory;
import com.iteaj.util.module.aop.ActionRecord;
import com.iteaj.util.module.aop.RecordType;
import com.iteaj.util.module.aop.WeaveAction;
import com.iteaj.util.module.aop.record.TimeRecord;

import java.lang.reflect.Method;

/**
 * Create Date By 2016/10/28
 *  <p>生成时间监控动作的工厂</p> <br>
 *  @see #getWeaveAction(Method, Class) 用来创建时间监控动作{@link WeaveAction}
 * @author iteaj
 * @since 1.7
 */
public abstract class TimeWeaveActionFactory
        extends AbstractWeaveActionFactory {

    @Override
    protected WeaveAction getWeaveAction(Method method, Class target) {
        final TimeWeaveActionFactory handle = this;
        return new WeaveAction<TimeRecord>(method, target) {
            /**被监控方法执行后的时间*/
            private long afterTime;
            /**被监控方法执行前的时间*/
            private long beforeTime;
            /**是否异常*/
            private boolean isException;

            @Override
            public String getIdentifier() {
                return handle.getIdentifier();
            }

            @Override
            protected void beforeMethodAction(Object ori, Object... args) {
                beforeTime = System.currentTimeMillis();
            }

            @Override
            protected void afterMethodAction(Object ori, Object returnVal, Object... args) {
                afterTime = System.currentTimeMillis();
            }

            @Override
            public void throwableAction(Throwable throwable, Object ori, Object... args) {
                this.isException = true;
            }

            @Override
            protected TimeRecord createRecord() throws IllegalAccessException, InstantiationException {
                TimeRecord instance = (TimeRecord)getRecord().newInstance();
                instance.setTime(afterTime-beforeTime);
                instance.setAction(this);
                if(isException)
                    instance.setRecordType(RecordType.Exception);

                return instance;
            }
        };
    }

    /**
     * 作为监控动作的标识
     * @return
     */
    public abstract String getIdentifier();

    @Override
    public boolean isMatchingRecord(Class<? extends ActionRecord> clazz) {
        if(clazz == null) return false;

        return TimeRecord.class.isAssignableFrom(clazz);

    }

}
