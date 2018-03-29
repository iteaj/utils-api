package com.iteaj.util.module.upload;

import java.util.Set;

/**
 * Create Date By 2016/8/31
 *
 * @author iteaj
 * @since 1.7
 */
public interface FilePropertyFilterRegistry {

    void register(Filter filter);

    Set<Filter> getFilters();
}
