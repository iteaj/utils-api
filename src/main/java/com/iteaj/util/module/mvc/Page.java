package com.iteaj.util.module.mvc;

import com.iteaj.util.module.mvc.orm.Entity;

import java.util.Collections;
import java.util.List;

/**
 * create time: 2018/7/20
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class Page<T extends Entity> {

    private List<T> records;
    private Long total;
    private long size;
    private long current;

    public Page() {
        this.records = Collections.emptyList();
        this.total = 0L;
        this.size = 10L;
        this.current = 1L;
    }

    public Page(long current, long size) {
        this(current, size, 0L);
    }

    public Page(long current, long size, Long total) {
        this.records = Collections.emptyList();
        this.total = 0L;
        this.size = 10L;
        this.current = 1L;
        if (current > 1L) {
            this.current = current;
        }

        this.size = size;
        this.total = total;
    }

    public long getPages() {
        if (getSize() == 0) {
            return 0L;
        }
        long pages = getTotal() / getSize();
        if (getTotal() % getSize() != 0) {
            pages++;
        }
        return pages;
    }

    public boolean hasPrevious() {
        return this.current > 1L;
    }

    public boolean hasNext() {
        return this.current < this.getPages();
    }

    public List<T> getRecords() {
        return this.records;
    }

    public Page<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    public Page<T> setTotal(Long total) {
        this.total = total;
        return this;
    }

    public Long getTotal() {
        return this.total;
    }

    public Page<T> setSize(long size) {
        this.size = size;
        return this;
    }

    public long getSize() {
        return this.size;
    }

    public Page<T> setCurrent(long current) {
        this.current = current;
        return this;
    }

    public long getCurrent() {
        return this.current;
    }

}
