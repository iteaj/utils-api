package com.iteaj.util.module.mvc.orm;

import com.iteaj.util.module.mvc.Page;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * create time: 2018/7/5
 *  dao层基类
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public interface IBaseDao<T extends Entity> {

    Integer insert(T entity);

    Integer deleteById(Serializable id);

    Integer deleteByBatch(Collection<? extends Serializable> ids);

    Integer updateById(T entity);

    List<T> selectByBatch(Collection<? extends Serializable> ids);

    T selectById(Serializable id);
}
