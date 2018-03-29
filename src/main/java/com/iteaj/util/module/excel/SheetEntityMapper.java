package com.iteaj.util.module.excel;

import java.util.List;

/**
 * Excel表格的Sheet列与实体类的字段映射
 * Create Date By 2017-05-10
 * @author iteaj
 * @since 1.7
 */
public interface SheetEntityMapper<T> {

    /**
     * excel行所对应的实体类类型信息
     * @return
     */
    Class<T> getEntityClass();

    /**
     * 获取excel字段信息和实体类字段的对应关系
     * @return
     */
    List<ExcelFiled> getEntityFields();

    /**
     * 标题所在行数
     * @return
     */
    int titleRow();
    
    /**
     * 获取excel搜索字段标头；
     * @return
     */
    
    List<String[]> getSearchCondition();
}
