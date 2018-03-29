package com.iteaj.util.module.excel;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Create Date By 2017-05-09
 * @author iteaj
 * @since 1.7
 */
public class ExcelHandle {

    private Workbook workbook;

    public ExcelHandle(InputStream inputStream) {
        try {
            this.workbook = WorkbookFactory.create(inputStream);
        } catch (Exception e) {
            throw new ExcelException(e);
        }
    }

    public Sheet getSheet(String name){
        return workbook.getSheet(name);
    }

    public Sheet getSheetAt(int i){
        return workbook.getSheetAt(i);
    }

    public List<Sheet> getSheets(){
        List<Sheet> sheets = new ArrayList<>();
        for(int i=0; i<workbook.getNumberOfSheets() ; i++){
            sheets.add(workbook.getSheetAt(i));
        }

        return sheets;
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }
}
