package com.iteaj.util;

import com.iteaj.util.module.mvc.Page;
import com.iteaj.util.module.mvc.orm.Entity;

import java.util.Collection;

/**
 * create time: 2018/7/19
 * @see T 数据的具体呈现对象, 比如Json{@link com.iteaj.util.module.json.Json}、xml、string数据
 * @see S 相应给客户端的状态, 比如：true/false、{@link IErrorCode}
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public interface Response<T, S> {

    S getStatus();

    Response setStatus(S status);

    String getErrMsg();

    Response setErrMsg(String errMsg);

    Response add(String key, Object val);

    /**
     * 增加分页对象到数据集{@code T}
     * @param page
     * @return
     */
    Response add(Page page);

    /**
     * 增加记录列表到数据集{@code T}
     * @param records
     * @return
     */
    Response add(Collection records);

    /**
     * 增加单条实体记录到数据集{@code T}
     * @param detail
     * @return
     */
    Response add(Entity detail);

    T getData();
}
