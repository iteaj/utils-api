package com.iteaj.util.module.excel.type;

import com.iteaj.util.module.excel.ExcelCellType;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Create Date By 2017-05-22
 *
 * @author iteaj
 * @since 1.7
 */
public interface ImageType extends ExcelCellType<byte[]> {

    BufferedImage getImage() throws IOException;

}
