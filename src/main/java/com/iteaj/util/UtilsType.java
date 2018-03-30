package com.iteaj.util;

/**
 * Create Date By 2018-03-30
 *
 * @author iteaj
 * @since 1.7
 */
public enum UtilsType {

    HTTP("http"), JSON("json");

    public String desc;

    UtilsType(String desc) {
        this.desc = desc;
    }
}
