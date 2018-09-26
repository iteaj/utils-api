package com.iteaj.util;

import com.iteaj.util.module.mvc.IPage;
import com.iteaj.util.module.mvc.orm.Entity;

import java.beans.Transient;
import java.util.Collection;

/**
 * create time: 2018/7/19
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public abstract class Response<This extends Response> {

    private String errMsg;

    public Response(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public This setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return (This) this;
    }

    /**
     * 增加分页对象到数据集
     * @param page
     * @return
     */
    public This add(IPage page) {
        this.add("page", page);
        return (This) this;
    }

    /**
     * 增加单条实体记录到数据集{@code T}
     * @param detail
     * @return
     */
    public This add(Entity detail) {
        this.add("detail", detail);
        return (This) this;
    }

    /**
     * 增加记录列表到数据集
     * @param records
     * @return
     */
    public This add(Collection records) {
        this.add("records", records);
        return (This) this;
    }

    public abstract Object getData();

    public abstract This add(String key, Object val);
}
