package com.iteaj.util.module.mvc;

import com.iteaj.util.module.mvc.orm.Entity;

import java.util.List;
import java.util.Map;

/**
 * create time: 2018/9/25
 *
 * @author iteaj
 * @since 1.0
 */
public interface IPage<T extends Entity> {

    /**
     * <p>
     * 降序字段集合
     * </p>
     *
     * @return
     */
    default List<String> descs() {
        return null;
    }

    /**
     * <p>
     * 升序字段集合
     * </p>
     *
     * @return
     */
    default List<String> ascs() {
        return null;
    }

    /**
     * <p>
     * 计算当前分页偏移量
     * </p>
     *
     * @return
     */
    default long offset() {
        return getCurrent() > 0 ? (getCurrent() - 1) * getSize() : 0;
    }

    /**
     * <p>
     * 当前分页总页数
     * </p>
     *
     * @return
     */
    default long getPages() {
        if (getSize() == 0) {
            return 0L;
        }
        long pages = getTotal() / getSize();
        if (getTotal() % getSize() != 0) {
            pages++;
        }
        return pages;
    }

    /**
     * <p>
     * 分页记录列表
     * </p>
     *
     * @return 分页对象记录列表
     */
    List<T> getRecords();

    /**
     * <p>
     * 设置分页记录列表
     * </p>
     *
     * @return 当前对象
     */
    IPage<T> setRecords(List<T> records);

    /**
     * <p>
     * 当前满足条件总行数
     * </p>
     * <p>
     * 当 total 为 null 或者大于 0 分页插件不在查询总数
     * </p>
     *
     * @return
     */
    Long getTotal();

    /**
     * <p>
     * 设置当前满足条件总行数
     * </p>
     * <p>
     * 当 total 为 null 或者大于 0 分页插件不在查询总数
     * </p>
     *
     * @return 当前对象
     */
    IPage<T> setTotal(Long total);

    /**
     * <p>
     * 当前分页总页数
     * </p>
     *
     * @return
     */
    long getSize();

    /**
     * <p>
     * 设置当前分页总页数
     * </p>
     *
     * @return
     */
    IPage<T> setSize(long size);

    /**
     * <p>
     * 当前页，默认 1
     * </p>
     *
     * @return
     */
    long getCurrent();

    /**
     * <p>
     * 设置当前页
     * </p>
     *
     * @return 当前对象
     */
    IPage<T> setCurrent(long current);
}
