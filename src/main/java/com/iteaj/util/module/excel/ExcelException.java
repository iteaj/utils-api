package com.iteaj.util.module.excel;

/**
 * Create Date By 2017-05-10
 *
 * @author iteaj
 * @since 1.7
 */
public class ExcelException extends RuntimeException {
    public ExcelException() {
    }

    public ExcelException(String message) {
        super(message);
    }

    public ExcelException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelException(Throwable cause) {
        super(cause);
    }

    public ExcelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
