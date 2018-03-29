package com.iteaj.util.module.upload.utils;

import com.iteaj.util.Generator;

import java.util.UUID;

/**
 * Create Date By 2016/8/29
 *  唯一文件名生成器
 * @author iteaj
 * @since 1.7
 */
public class FileNameGenerator implements Generator {

    @Override
    public synchronized String Generator() {
        return UUID.randomUUID().toString();
    }
}
