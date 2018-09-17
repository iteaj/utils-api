package com.iteaj.util.module.mvc.mybatisplus;

import com.iteaj.util.module.mvc.IBaseService;
import com.iteaj.util.module.mvc.Page;
import com.iteaj.util.module.mvc.orm.Entity;
import org.springframework.beans.factory.annotation.Autowired;

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
public abstract class MybatisPlusService<E extends Entity
        , D extends IMybatisPlusDao<E>> implements IBaseService<E, D> {

    @Autowired
    private D baseDao;

    @Override
    public D getBaseDao() {
        return baseDao;
    }

    @Override
    public boolean insert(E entity) {
        return baseDao.insert(entity)>0;
    }

    @Override
    public boolean deleteByBatch(Collection<Serializable> ids) {
        return getBaseDao().deleteBatchIds(ids) > 0;
    }

    @Override
    public boolean deleteById(Serializable id) {
        return baseDao.deleteById(id) > 0;
    }

    @Override
    public boolean updateById(E entity) {
        return baseDao.updateById(entity) > 0;
    }

    @Override
    public E getById(Serializable id) {
        return baseDao.selectById(id);
    }

    @Override
    public boolean delete(E entity) {
        throw new UnsupportedOperationException("请在子类覆写");
    }

    @Override
    public boolean update(E entity) {
        throw new UnsupportedOperationException("请在子类覆写");
    }

    @Override
    public List<E> find(E entity) {
        throw new UnsupportedOperationException("请在子类覆写");
    }

    @Override
    public Page<E> findByPage(Page<E> page, E entity) {
        throw new UnsupportedOperationException("请在子类覆写");
    }
}
