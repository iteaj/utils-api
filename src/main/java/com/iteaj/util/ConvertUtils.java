package com.iteaj.util;

/**
 * create time: 2018/7/18
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public abstract class ConvertUtils {

    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String toHex(byte[] bytes) {
        int l = bytes.length;
        char[] out = new char[l << 1];

        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS_LOWER[(0xF0 & bytes[i]) >>> 4];
            out[j++] = DIGITS_LOWER[0x0F & bytes[i]];
        }
        return new String(out);
    }
}
