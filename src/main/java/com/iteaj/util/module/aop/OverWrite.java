package com.iteaj.util.module.aop;

/**
 * Create Date By 2018-03-26
 *  是否可以被覆写
 *  用于指定子类是否要覆盖掉父类在{@link java.util.HashMap#put(Object, Object)}里面
 *  Map元素对象必须重写 {@link Object#hashCode()} 和 {@link Object#equals(Object)}方法
 * @author iteaj
 * @since 1.7
 */
public interface OverWrite {

    /**
     * 返回一个标记, 是否可以覆写
     * @return
     */
    boolean isOverride();

    /**
     * 设置可覆写标志
     */
    void setOverride(boolean flag);

}
