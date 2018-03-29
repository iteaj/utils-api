package com.iteaj.util.module.excel;

/**
 * Create Date By 2017-12-29
 * @author iteaj
 * @since 1.7
 */
public class FreezePoint {

    private int cellNum;
    private int rowNum;
    private int firstCellNum;
    private int firstRollNum;

    public FreezePoint(int cellNum, int rowNum) {
        this.cellNum = cellNum;
        this.rowNum = rowNum;
    }

    public FreezePoint(int cellNum, int rowNum, int firstCellNum, int firstRollNum) {
        this.cellNum = cellNum;
        this.rowNum = rowNum;
        this.firstCellNum = firstCellNum;
        this.firstRollNum = firstRollNum;
    }

    public int getCellNum() {
        return cellNum;
    }

    public void setCellNum(int cellNum) {
        this.cellNum = cellNum;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getFirstCellNum() {
        return firstCellNum;
    }

    public void setFirstCellNum(int firstCellNum) {
        this.firstCellNum = firstCellNum;
    }

    public int getFirstRollNum() {
        return firstRollNum;
    }

    public void setFirstRollNum(int firstRollNum) {
        this.firstRollNum = firstRollNum;
    }
}
