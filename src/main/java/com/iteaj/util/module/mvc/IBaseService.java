package com.iteaj.util.module.mvc;

import com.iteaj.util.module.mvc.orm.Entity;
import com.iteaj.util.module.mvc.orm.IBaseDao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * create time: 2018/7/20
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public interface IBaseService<T extends Entity, D extends IBaseDao<T>> {

    D getBaseDao();

    /**
     * 插入所有列
     * @param entity
     * @return
     */
    default boolean insert(T entity){
        return getBaseDao().insert(entity) > 0;
    }

    /**
     * 删除通过id
     *
     * @param id
     * @return
     */
    default boolean deleteById(Serializable id) {
        return getBaseDao().deleteById(id) > 0;
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    default boolean deleteByBatch(Collection<Serializable> ids) {
        return getBaseDao().deleteByBatch(ids) > 0;
    }

    /**
     * 通过Id更新所有列
     * @param entity
     * @return
     */
    default boolean updateById(T entity) {
       return getBaseDao().updateById(entity) > 0;
    }

    /**
     * 通过Id获取一条记录
     * @param id
     * @return
     */
    default T selectById(Serializable id) {
        return getBaseDao().selectById(id);
    }

    /**
     * 批量查询, 通过批量的Id
     * @param ids
     * @return
     */
    default List<T> selectByBatch(Collection<? extends Serializable> ids) {
        return getBaseDao().selectByBatch(ids);
    }
}
