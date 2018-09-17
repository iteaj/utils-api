package com.iteaj.util;

import java.util.Random;

/**
 * create time: 2018/7/18
 *  随机数工具类
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public abstract class RandomUtils {

    public static final char[] NUMBER = "0123456789".toCharArray();
    public static final char[] CHAR26 = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    public static final char[] CHAR62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    public static final char[] CHAR_ALL = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!%^()_+-=<>?,./".toCharArray();

    public enum Type {
        L10, L26, L62, LA
    }

    public static String create(int length, Type type) {
        Random random = new Random(length);
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<length; i++) {
            sb.append(getChar(type, random));
        }

        return sb.toString();
    }

    protected static char getChar(Type type, Random random) {
        switch (type) {
            case L10: return NUMBER[random.nextInt(NUMBER.length-1)];
            case L26: return CHAR26[random.nextInt(CHAR26.length-1)];
            case L62: return CHAR62[random.nextInt(CHAR62.length-1)];
            case LA: return CHAR_ALL[random.nextInt(CHAR_ALL.length-1)];

            default: throw new IllegalArgumentException();
        }
    }
}
