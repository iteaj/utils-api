package com.iteaj.util.module.excel;

/**
 * Create Date By 2017-05-22
 *
 * @author iteaj
 * @since 1.7
 */
public class ExcelPicture {

    private int width; //图片宽
    private int height; //图片高

    public ExcelPicture(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
