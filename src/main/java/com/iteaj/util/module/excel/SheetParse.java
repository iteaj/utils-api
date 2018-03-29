package com.iteaj.util.module.excel;

import org.apache.poi.ss.usermodel.Sheet;

/**
 * Create Date By 2017-05-10
 *
 * @author iteaj
 * @since 1.7
 */
public interface SheetParse<T> {

    /**
     * 解析{@link Sheet}到实体对象
     * @param sheetMapper
     * @return
     */
    T parse(Sheet sheet, SheetEntityMapper sheetMapper) throws ReflectiveOperationException;
}
