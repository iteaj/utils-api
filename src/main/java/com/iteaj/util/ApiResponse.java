package com.iteaj.util;

import com.iteaj.util.module.json.Json;
import com.iteaj.util.module.mvc.Page;
import com.iteaj.util.module.mvc.orm.Entity;

import java.beans.Transient;
import java.util.Collection;

/**
 * create time: 2018/7/20
 *  Rest Api响应对象
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class ApiResponse implements Response<Json, IErrorCode> {

    private Json json;
    private String errCode;
    private String errMsg;
    private IErrorCode errorCode;

    public ApiResponse(IErrorCode code) {
        if(code != null) {
            this.errCode = code.getErrCode();
            this.setErrMsg(code.getErrMsg());
        }

        this.json = JsonUtils.buildJson();
    }

    @Override
    @Transient
    public IErrorCode getStatus() {
        return errorCode;
    }

    @Override
    public ApiResponse setStatus(IErrorCode status) {
        this.errorCode = status;
        return this;
    }

    @Override
    public String getErrMsg() {
        return errMsg;
    }

    @Override
    public ApiResponse setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }

    @Override
    public ApiResponse add(String key, Object val) {
        json.addNode(key, val);
        return this;
    }

    @Override
    public Response add(Page page) {
        return add("page", page);
    }

    @Override
    public Response add(Collection records) {
        return add("records", records);
    }

    @Override
    public Response add(Entity detail) {
        return add("detail", detail);
    }

    @Override
    public Json getData() {
        return json;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }
}
