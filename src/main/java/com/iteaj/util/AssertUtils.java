package com.iteaj.util;

import com.iteaj.util.core.UtilsException;
import com.iteaj.util.core.UtilsType;

/**
 * Create Date By 2018-04-03
 *
 * @author iteaj
 * @since 1.7
 */
public abstract class AssertUtils {

    /**
     * 断言字符串存在  str != "" && str != null
     * @param str
     * @param msg
     * @param type
     */
    public static void isNotBlank(String str, String msg, UtilsType type) {
        if(!CommonUtils.isNotBlank(str))
            throw new UtilsException(msg, type);

    }

    public static void isTrue(boolean expression, String msg, UtilsType type) {
        if(!expression)
            throw new UtilsException(msg, type);
    }
}
