package com.iteaj.util.module.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel 解析字段注解
 * Create Date By 2017-05-08
 * @author iteaj
 * @since 1.7
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface Excel {
    /**
     * 字段名称
     * @return
     */
    String name();

    /**
     * 字段排序
     * @return
     */
    int order();

}
