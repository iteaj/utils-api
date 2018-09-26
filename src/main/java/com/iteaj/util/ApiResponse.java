package com.iteaj.util;

import com.iteaj.util.module.json.Json;

/**
 * create time: 2018/7/20
 *  Rest Api响应对象
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class ApiResponse extends Response<ApiResponse> {

    private Json json;
    private IErrorCode errorCode;

    public ApiResponse(IErrorCode code) {
        super(code == null ? null : code.getErrMsg());
        this.errorCode = code;
        this.json = JsonUtils.builder();
    }

    public String getErrCode() {
        return errorCode == null ? null : errorCode.getErrCode();
    }

    @Override
    public Object getData() {
        return json.original();
    }

    @Override
    public ApiResponse add(String key, Object val) {
        json.add(key, val);
        return this;
    }
}
