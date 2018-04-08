package com.iteaj.util.module.http;

/**
 * create time: 2018/4/7
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class HttpHead {
    private String name;
    private String value;

    public HttpHead(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
