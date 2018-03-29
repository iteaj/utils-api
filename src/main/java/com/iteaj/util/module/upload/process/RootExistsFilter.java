package com.iteaj.util.module.upload.process;

import com.iteaj.util.module.upload.Configuration;
import com.iteaj.util.module.upload.Filter;
import com.iteaj.util.module.upload.Resource.UploadResource;

/**
 * Create Date By 2016/8/31
 *  此过滤器用来过滤上传的根目录是否存在
 * @author iteaj
 * @since 1.7
 */
public class RootExistsFilter extends Filter {

    @Override
    public void doFilter(UploadResource resource, Configuration configuration) {
        if(!configuration.isExists())
            throw new RuntimeException("文件上传的根目录不存在,请先初始化");
    }

    @Override
    protected Class<?> getCurrentClass() {
        return RootExistsFilter.class;
    }
}
