package com.iteaj.util.module.upload.transfer;

import com.iteaj.util.module.upload.Resource.UploadResource;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Create Date By 2016/8/29
 *  文件输送目标
 * @author iteaj
 * @since 1.7
 */
public abstract class TransferTarget {

    /**
     * 文件传输
     * @param desc  传输目标
     */
    public abstract void transferTo(UploadResource resource, File desc) throws IOException;

    /**
     * 文件传输
     * @param outputStream  目标文件输出流
     */
    public abstract void transferTo(UploadResource resource, OutputStream outputStream) throws IOException;
}
