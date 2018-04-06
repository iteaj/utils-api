package com.iteaj.util.core;

/**
 * Create Date By 2018-03-30
 *
 * @author iteaj
 * @since 1.7
 */
public class UtilsException extends RuntimeException {

    private UtilsType type;

    public UtilsException(UtilsType type) {
        super(type.desc);
        this.type = type;
    }

    public UtilsException(String message, UtilsType type) {
        super(message+type.desc);
        this.type = type;
    }

    public UtilsException(String message, Throwable cause, UtilsType type) {
        super(message+type.desc, cause);
        this.type = type;
    }

    public UtilsType getType() {
        return type;
    }

    public void setType(UtilsType type) {
        this.type = type;
    }
}
