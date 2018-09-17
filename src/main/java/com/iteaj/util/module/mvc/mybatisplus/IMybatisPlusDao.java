package com.iteaj.util.module.mvc.mybatisplus;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iteaj.util.module.mvc.orm.Entity;
import com.iteaj.util.module.mvc.orm.IBaseDao;

/**
 * create time: 2018/7/20
 *  提供Mybatis-plus Dao层支持
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public interface IMybatisPlusDao<T extends Entity> extends IBaseDao<T>, BaseMapper<T> {

}
