package com.iteaj.util;

/**
 * Create Date By 2018-03-30
 *
 * @author iteaj
 * @since 1.7
 */
public class UtilsException extends RuntimeException {

    private UtilsType type;

    public UtilsException(UtilsType type) {
        this.type = type;
    }

    public UtilsException(String message, UtilsType type) {
        super(message);
        this.type = type;
    }

    public UtilsException(String message, Throwable cause, UtilsType type) {
        super(message, cause);
        this.type = type;
    }

    public UtilsException(Throwable cause, UtilsType type) {
        super(cause);
        this.type = type;
    }
}
