package com.iteaj.util.module.aop;

import com.iteaj.util.module.aop.output.Slf4JActionOutput;

/**
 * Create Date By 2016/10/27
 *  织入动作{@link WeaveAction#}产生输出 例如：日志输出{@link Slf4JActionOutput}
 *  泛型R,用来指定输出的记录类型,规定一种监控输出{@link ActionOutput}只能输出一种监控记录{@link ActionRecord}<br>
 *      每一种监控动作都会生成与之对应的监控记录,此记录将会被对应的监控输出器{@link ActionOutput}输出
 * @author iteaj
 * @since 1.7
 */
public abstract class ActionOutput implements OverWrite, Cloneable {

    private boolean start;
    private boolean override;

    public ActionOutput() {

    }

    /**
     * 用来指定是否开启此输出功能
     * @return
     */
    public boolean isStart(){
        return start;
    }

    /**
     * 设置是否启用
     */
    public void setStart(boolean start){
        this.start = start;
    }

    /**
     * 返回是否覆写掉父类
     * @return
     */
    public boolean isOverride() {
        return override;
    }

    /**
     * 如果为true 则子类覆盖掉父类 {@link java.util.Set#equals(Object)}
     * @param override
     */
    public void setOverride(boolean override) {
        this.override = override;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    public boolean equals(Object obj) {
        //同一个Class类型说明是同一个类
        if(obj.getClass() == this.getClass())
            return true;

        //如果是obj的子类, 并且{@code getOverride()}可以被覆写,则直接覆盖掉父类
        if(obj.getClass().isAssignableFrom(getClass()) && isOverride())
            return true;

        return false;
    }

    /**
     *  将监控记录写出指定目标
     * @param record
     */
    public abstract void write(ActionRecord record) throws Exception;

    /**
     * <p>用来匹配指定记录是否可以被此输出器输出</p>
     * 因为一种监控输出{@link ActionOutput}只能输出与之对应的监控记录{@link ActionRecord}
     *
     * @param record
     * @return
     */
    public abstract boolean isMatching(ActionRecord record);
}
