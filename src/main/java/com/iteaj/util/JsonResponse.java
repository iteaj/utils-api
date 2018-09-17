package com.iteaj.util;

import com.iteaj.util.module.json.Json;
import com.iteaj.util.module.mvc.Page;
import com.iteaj.util.module.mvc.orm.Entity;

import java.util.Collection;

/**
 * create time: 2018/7/19
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class JsonResponse implements Response<Json, Boolean> {

    private Json json;
    private String errMsg;
    private Boolean status;

    public JsonResponse() {
        this(false, null);
    }

    public JsonResponse(boolean status) {
        this(status, null);
    }

    public JsonResponse(String errMsg) {
        this(false, errMsg);
    }

    public JsonResponse(boolean status, String errMsg) {
        this.status = status;
        this.errMsg = errMsg;
        this.json = JsonUtils.buildJson();
    }

    @Override
    public Boolean getStatus() {
        return status == null ? false : status;
    }

    @Override
    public JsonResponse setStatus(Boolean status) {
        this.status = status;
        return this;
    }

    @Override
    public String getErrMsg() {
        return errMsg;
    }

    @Override
    public JsonResponse setErrMsg(String message) {
        this.errMsg = message;
        return this;
    }

    @Override
    public JsonResponse add(String key, Object val) {
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
}
