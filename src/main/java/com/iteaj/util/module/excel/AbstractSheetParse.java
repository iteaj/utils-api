package com.iteaj.util.module.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * Create Date By 2017-05-15
 *
 * @author iteaj
 * @since 1.7
 */
public abstract class AbstractSheetParse<T> implements SheetParse<T> {

    protected Object getRowEntity(Row row, Class clazz){
        return null;
    }

    protected Object getCellProperty(Cell cell, ExcelFiled filed){

        return null;
    }
}
