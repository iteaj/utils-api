package com.iteaj.util;

import java.util.List;

/**
 * Create Date By 2018-03-30
 *
 * @author iteaj
 * @since 1.7
 */
public abstract class CommonUtils {

    public static boolean isNotBlank(String name) {
        return name != null && name.length() > 0;
    }

    public static boolean isNotEmpty(List<?> params) {
        return null != params && params.size() > 0;
    }
}
