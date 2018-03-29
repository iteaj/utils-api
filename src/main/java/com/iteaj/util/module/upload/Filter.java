package com.iteaj.util.module.upload;

import com.iteaj.util.module.upload.Resource.UploadResource;

/**
 * Create Date By 2016/8/30
 * 过滤器
 *  描述：用来对上传的文件的一些属性进行过滤
 * @author iteaj
 * @since 1.7
 */
public abstract class Filter {

    public abstract void doFilter(UploadResource resource, Configuration configuration);

    protected abstract Class<?> getCurrentClass();

    @Override
    public boolean equals(Object obj) {
        return obj.getClass().isAssignableFrom(getCurrentClass());
    }
}
