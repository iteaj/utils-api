package com.iteaj.util.module.upload.process;

import com.iteaj.util.module.upload.Configuration;
import com.iteaj.util.module.upload.Filter;
import com.iteaj.util.module.upload.Resource.UploadResource;

/**
 * Create Date By 2016/8/30
 *  文件大小过滤
 * @author iteaj
 * @since 1.7
 */
public class FileSizeFilter extends Filter {

    @Override
    public void doFilter(UploadResource resource, Configuration configuration) {
//        if(resource.isEmpty() && null == resource.getFileName())
//            throw
    }

    @Override
    protected Class<?> getCurrentClass() {
        return FileSizeFilter.class;
    }
}
