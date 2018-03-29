package com.iteaj.util.module.upload.Resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * Create Date By 2016/8/29
 *  文件上传的文件来源
 * @author iteaj
 * @since 1.7
 */
public interface UploadResource {

    /**
     * 获取文件名
     * @return
     */
    String getFileName();

    /**
     * 获取文件类型
     * @return
     */
    String getContentType();

    /**
     * 获取文件大小
     * @return
     */
    long getSize();

    /**
     * 获取表单名称
     * @return
     */
    String getFormName();

    /**
     * 获取文件输入流
     * @return
     */
    InputStream getInputStream() throws IOException;

    /**
     * 获取文件字节内容
     * @return
     */
    byte[] getByteArray() throws IOException;

    /**
     * 判断文件是不是空
     * @return
     */
    boolean isEmpty();

    /**
     * 获取文件后缀(.jpg)
     * @return
     */
    String getSuffix();

    /**
     * 判断文件是否存在(根据有文件名和文件大小来判断)
     * @return
     */
    boolean isExist();
}
