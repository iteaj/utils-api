package com.iteaj.util.module.mvc.orm;

import java.io.Serializable;

/**
 * create time: 2018/7/5
 *  实体接口基类
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public interface Entity<T extends Serializable> extends Serializable {

    /**
     * 返回实体标识 和数据库对应
     * @return
     */
    T getId();

    void setId(T id);
}
