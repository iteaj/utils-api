package com.iteaj.util.module.mvc.mybatisplus;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.iteaj.util.module.mvc.Page;
import com.iteaj.util.module.mvc.orm.Entity;

import java.util.List;
import java.util.Map;

/**
 * create time: 2018/7/20
 *  提供Mybatis-Plus 的分页适配
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class PlusPage<E extends Entity> extends Page<E> implements IPage<E> {

    private List<String> ascs;
    private List<String> descs;

    public PlusPage() {

    }

    public PlusPage(long current, long size) {
        super(current, size);
    }

    public PlusPage(long current, long size, Long total) {
        super(current, size, total);
    }

    @Override
    public long getPages() {
        return super.getPages();
    }

    public Page convertPage() {
       return new Page(this.getCurrent(), this.getSize()
                , this.getTotal()).setRecords(this.getRecords());
    }

    @Override
    public PlusPage<E> setCurrent(long current) {
        super.setCurrent(current);
        return this;
    }

    @Override
    public PlusPage<E> setTotal(Long total) {
        super.setTotal(total);
        return this;
    }

    @Override
    public PlusPage<E> setRecords(List<E> records) {
        super.setRecords(records);
        return this;
    }

    @Override
    public PlusPage<E> setSize(long size) {
        super.setSize(size);
        return this;
    }

    @Override
    public List<String> descs() {
        return descs;
    }

    @Override
    public List<String> ascs() {
        return ascs;
    }

    public PlusPage setAscs(List<String> ascs) {
        this.ascs = ascs;
        return this;
    }

    public PlusPage setDescs(List<String> descs) {
        this.descs = descs;
        return this;
    }

    @Override
    public Map<Object, Object> condition() {
        return null;
    }

    @Override
    public boolean optimizeCountSql() {
        return true;
    }

    @Override
    public long offset() {
        return getCurrent() > 0 ? (getCurrent() - 1) * getSize() : 0;
    }
}
