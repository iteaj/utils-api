package com.iteaj.util.module.aop;

import com.iteaj.util.module.aop.factory.ExceptionWeaveActionFactory;
import com.iteaj.util.module.aop.factory.time.TimeWeaveActionFactory;
import com.iteaj.util.module.aop.record.VoidRecord;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.Method;

/**
 * Create Date By 2016/10/27
 * <p>用来创建织入动作的工厂</p><br>
 * 织入工厂{@link AbstractWeaveActionFactory}用来创建织入动作{@link WeaveAction},
 *  例如：异常动作工厂{@link ExceptionWeaveActionFactory}以及
 *  时间动作工厂{@link TimeWeaveActionFactory},前者是用来生产
 *  用来异常动作而或者是用来生产时间动作.
 *
 *  @see #getWeaveAction(Method, Class) 此方法用来生产动作,请确保每个生成的动作是不同的实例,否则会产生并发问题
 * @author iteaj
 * @since 1.7
 */
public abstract class AbstractWeaveActionFactory
        implements OverWrite, InitializingBean, Comparable<AbstractWeaveActionFactory> {

    /**
     * 指定此动作是否启用
     * 默认为：true
     */
    private boolean isStart;
    private boolean override;
    private int hashCode = 0;
    private Class<? extends ActionRecord> record;

    /**
     * 动作执行顺序
     * @return
     */
    public Integer order(){
        return 0;
    }

    /**
     * 动作{@link WeaveAction}
     * @return
     */
    protected abstract WeaveAction getWeaveAction(Method method, Class<?> target);

    /**
     * 是否监控此方法
     * @param method
     * @param targetClass
     * @return
     */
    public abstract boolean isMonitoring(Method method, Class<?> targetClass);

    /**
     * @see WeaveAction 动作
     * @see ActionOutput 输出
     * @see ActionRecord 记录
     *
     * 用来匹配此动作是否可以.
     * 一种动作只生成一种类型的记录{@link ActionRecord},
     * 而每一种记录{@link ActionRecord}可以有多个输出{@link ActionOutput},
     * 所以必须要求每一种记录类型必须匹配此动作的指定记录类型
     * @param clazz
     * @return
     */
    public abstract boolean isMatchingRecord(Class<? extends ActionRecord> clazz);

    @Override
    public void afterPropertiesSet() throws Exception {
        if(null == getRecord())
            throw new IllegalArgumentException("未指定此动作要输出的记录类型,这将导致此动作无输出："
                    +ActionRecord.class.getName()+" 如果不需要输出用："+ VoidRecord.class.getName());

        //此动作不匹配记录类型
        if(!isMatchingRecord(getRecord())) {
            throw new IllegalStateException("动作和记录不匹配："+ getClass().getSimpleName()
                    +"/"+getRecord().getSimpleName());
        }
    }

    @Override
    public boolean isOverride() {
        return override;
    }

    @Override
    public void setOverride(boolean flag) {
        this.override = flag;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public Class<? extends ActionRecord> getRecord() {
        return record;
    }

    public void setRecord(Class<? extends ActionRecord> record) {
        this.record = record;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() == getClass())
            return true;

        //如果是obj的子类, 并且{@code getOverride()}可以被覆写,则直接覆盖掉父类
        if(obj.getClass().isAssignableFrom(getClass()) && isOverride())
            return true;

        return false;
    }

    @Override
    public int compareTo(AbstractWeaveActionFactory o) {
        if(o == this) return 0;
        if(this.order()==0) return 1;
        if(order() > o.order()) return 1;
        else if(order() < o.order()) return -1;
        else return 0;
    }
}
