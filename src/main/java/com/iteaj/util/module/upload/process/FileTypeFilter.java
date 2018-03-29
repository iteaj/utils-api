package com.iteaj.util.module.upload.process;

import com.iteaj.util.module.upload.Configuration;
import com.iteaj.util.module.upload.Filter;
import com.iteaj.util.module.upload.Resource.UploadResource;

/**
 * Create Date By 2016/8/30
 *  文件类型过滤器
 * @author iteaj
 * @since 1.7
 */
public class FileTypeFilter extends Filter {
    @Override
    public void doFilter(UploadResource resource, Configuration configuration) {

    }

    @Override
    protected Class<?> getCurrentClass() {
        return FileTypeFilter.class;
    }
}
