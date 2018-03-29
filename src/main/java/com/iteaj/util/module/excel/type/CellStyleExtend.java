package com.iteaj.util.module.excel.type;

import com.iteaj.util.module.excel.ExcelCellType;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

/**
 * Create Date By 2017-12-26
 *
 * @author iteaj
 * @since 1.7
 */
public interface CellStyleExtend extends ExcelCellType<String>{

    void setStyle(XSSFCellStyle style);
}
