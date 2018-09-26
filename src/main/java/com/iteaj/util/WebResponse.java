package com.iteaj.util;

import com.iteaj.util.module.json.Json;
import com.iteaj.util.module.mvc.IPage;
import com.iteaj.util.module.mvc.Page;
import com.iteaj.util.module.mvc.orm.Entity;
import org.apache.poi.ss.formula.functions.T;

import java.beans.Transient;
import java.util.Collection;

/**
 * create time: 2018/7/19
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class WebResponse extends Response<WebResponse> {

    private Json json;
    private boolean status;

    public WebResponse() {
        this(false, null);
    }

    public WebResponse(boolean status) {
        this(status, null);
    }

    public WebResponse(String errMsg) {
        this(false, errMsg);
    }

    public WebResponse(boolean status, String errMsg) {
        super(errMsg);
        this.status = status;
        this.json = JsonUtils.builder();
    }

    @Override
    public Object getData() {
        return json.original();
    }

    @Override
    public WebResponse add(String key, Object val) {
        json.add(key, val);
        return this;
    }

    public boolean getStatus() {
        return status;
    }

    public WebResponse setStatus(boolean status) {
        this.status = status;
        return this;
    }
}
