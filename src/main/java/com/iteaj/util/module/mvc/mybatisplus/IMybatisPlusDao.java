package com.iteaj.util.module.mvc.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iteaj.util.module.mvc.orm.Entity;
import com.iteaj.util.module.mvc.orm.IBaseDao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * create time: 2018/7/20
 *  提供Mybatis-plus Dao层支持
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public interface IMybatisPlusDao<T extends Entity> extends IBaseDao<T>, BaseMapper<T> {

    @Override
    PlusPage<T> selectPage(IPage<T> page, Wrapper<T> queryWrapper);

    @Override
    default Integer deleteByBatch(Collection<? extends Serializable> ids) {
        return deleteBatchIds(ids);
    }

    @Override
    default List<T> selectByBatch(Collection<? extends Serializable> ids) {
        return selectBatchIds(ids);
    }
}
