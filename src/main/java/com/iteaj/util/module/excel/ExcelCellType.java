package com.iteaj.util.module.excel;

/**
 * Create Date By 2017-05-22
 *
 * @author iteaj
 * @since 1.7
 */
public interface ExcelCellType<T> {

    /**
     * 判断此值{@code value}是否是当前类型
     * @param value
     * @return
     */
    boolean isMatcher(Object value);

    /**
     * 返回此单元格的值
     * @return
     */
    T cellValue() throws Exception;

}
