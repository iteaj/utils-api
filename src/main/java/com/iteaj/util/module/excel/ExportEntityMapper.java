package com.iteaj.util.module.excel;

/**
 * Create Date By 2017-05-22
 *
 * @author iteaj
 * @since 1.7
 */
public interface ExportEntityMapper<T> extends SheetEntityMapper<T> {

    /**
     * 返回Excel Sheet的名字
     * @return
     */
    String getSheetName();

    /**
     * 获取图片信息,如果excel需要插入图片
     * @return
     */
    ExcelPicture getPicture();

    /**
     * 返回冻结点,比如第一、二列冻结住,不能拖动
     * @return
     */
    FreezePoint getFreezePane();

}
