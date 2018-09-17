package com.iteaj.util.module.mvc;

/**
 * create time: 2018/7/22
 *  业务层异常
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
