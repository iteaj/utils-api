package com.iteaj.util.module.excel;

/**
 * Create Date By 2017-05-09
 *
 * @author iteaj
 * @since 1.7
 */
public class ExcelFiled {

    private String title;
    private boolean ignore;
    private String property;
    private boolean required;

    public ExcelFiled(String property) {
        this(property, null, false, false);
    }

    public ExcelFiled(String property, String title) {
        this(property, title, false, false);
    }

    public ExcelFiled(String property, String title, boolean ignore){
        this(property, title, ignore, false);
    }

    public ExcelFiled(String property, String title, boolean ignore, boolean required) {
        this.title = title;
        this.ignore = ignore;
        this.required = required;
        this.property = property;
    }

    public String getProperty() {
        return property;
    }

    public ExcelFiled setProperty(String property) {
        this.property = property;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ExcelFiled setTitle(String title) {
        this.title = title;
        return this;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public ExcelFiled setIgnore(boolean ignore) {
        this.ignore = ignore;
        return this;
    }

    public boolean isRequired() {
        return required;
    }

    public ExcelFiled setRequired(boolean required) {
        this.required = required;
        return this;
    }
}
