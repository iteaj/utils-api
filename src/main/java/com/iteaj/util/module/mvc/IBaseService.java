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
    boolean insert(T entity);

    /**
     * 通过指定条件删除记录
     * @param entity
     * @return
     */
    boolean delete(T entity);

    /**
     * 删除通过id
     *
     * @param id
     * @return
     */
    boolean deleteById(Serializable id);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    boolean deleteByBatch(Collection<Serializable> ids);

    /**
     * 更新, 通过指定的参数
     * @param entity
     * @return
     */
    boolean update(T entity);

    /**
     * 通过Id更新所有列
     * @param entity
     * @return
     */
    boolean updateById(T entity);

    /**
     * 通过Id获取一条记录
     * @param id
     * @return
     */
    T getById(Serializable id);

    /**
     * 查询并返回符合条件的数据集
     * @param entity
     * @return
     */
    List<T> find(T entity);

    /**
     * 查询通过分页
     * @param page
     * @param entity
     * @return
     */
    Page<T> findByPage(Page<T> page, T entity);
}
