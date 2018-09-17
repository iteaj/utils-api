package com.iteaj.util;

import com.iteaj.util.core.UtilsException;
import com.iteaj.util.core.UtilsType;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * create time: 2018/7/18
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class DigestUtils {

    /**
     * 生成SHA-1摘要
     * @param data
     * @return
     */
    public static byte[] sha1(byte[] data) {
        try {
            return MessageDigest.getInstance("SHA-1").digest(data);
        } catch (NoSuchAlgorithmException e) {
            throw new UtilsException(e.getMessage(), e, UtilsType.Digest);
        }
    }

    /**
     * 生成SHA-1摘要, 并且转为十六进制
     * @param data
     * @return
     */
    public static String sha1Hex(byte[] data) {
        return ConvertUtils.toHex(sha1(data));
    }

    /**
     * 生成MD5摘要
     * @param data
     * @return
     */
    public static byte[] md5(byte[] data) {
        try {
            return MessageDigest.getInstance("MD5").digest(data);
        } catch (NoSuchAlgorithmException e) {
            throw new UtilsException(e.getMessage(), e, UtilsType.Digest);
        }
    }

    /**
     * 生成MD5摘要, 并且转为十六进制
     * @param data
     * @return
     */
    public static String md5Hex(byte[] data) {
        return ConvertUtils.toHex(md5(data));
    }
}
