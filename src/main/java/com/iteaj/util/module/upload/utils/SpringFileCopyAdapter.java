package com.iteaj.util.module.upload.utils;

import org.springframework.util.FileCopyUtils;

import java.io.*;

/**
 * Create Date By 2016/8/29
 *  spring框架的文件复制适配器
 * @author iteaj
 * @since 1.7
 */
public class SpringFileCopyAdapter {

    public static void copy(byte[] bytes, File desc) throws IOException {
        existsAndTouch(desc);
        FileCopyUtils.copy(bytes, desc);
    }

    public static void copy(InputStream inputStream, File desc) throws IOException{
        existsAndTouch(desc);
        FileCopyUtils.copy(inputStream, new FileOutputStream(desc));
    }

    public static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        FileCopyUtils.copy(inputStream,outputStream);
    }

    public static void existsAndTouch(File desc) throws IOException {
        if(!desc.exists())
            desc.createNewFile();
    }
}
