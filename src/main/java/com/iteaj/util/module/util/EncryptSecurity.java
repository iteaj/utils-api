package com.iteaj.util.module.util;

/**
 * Create Date By 2016/9/8
 *  加密安全相关接口
 *  T为加密的返回值类型
 * @author iteaj
 * @since 1.7
 */
public interface EncryptSecurity<T> {

    /**
     * 加密
     * @param content 要加密的内容
     * @return
     */
    T encrypt(String content);

    /**
     * 加密
     * @param bytes 要加密的内容
     * @return
     */
    T encrypt(byte[] bytes);
}
