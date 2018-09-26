package com.iteaj.util;

/**
 * create time: 2018/7/22
 *  一般使用在对外开放Api的场景
 * @see ApiResponse
 * @see com.iteaj.util.module.mvc.ApiController
 * @see IErrorCode
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class ApiException extends RuntimeException {

    private IErrorCode code;

    public ApiException(IErrorCode code) {
        this.code = code;
    }

    public ApiException(Throwable cause, IErrorCode code) {
        super(cause);
        this.code = code;
    }

    public IErrorCode getCode() {
        return code;
    }
}
