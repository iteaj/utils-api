package com.iteaj.util.module.upload;

import com.iteaj.util.module.upload.Resource.UploadResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.Set;

/**
 * Create Date By 2016/8/29
 *  文件上传处理器
 * @author iteaj
 * @since 1.7
 */
public abstract class ResourceProcess {

    protected Logger logger = LoggerFactory.getLogger(ResourceProcess.class);
    protected Configuration configuration;

    protected ResourceProcess(Configuration configuration){
        this.configuration = configuration;
    }

    public AccessPath process(UploadResource resource) throws Exception {
        //调用文件属性验证过滤器
        invokeFilterChain(resource, configuration);

        //执行上传
        return doProcess(resource, configuration);
    }

    protected void invokeFilterChain(UploadResource resource,Configuration configuration) {
        Set<Filter> filters = configuration.getFilters();
        if(CollectionUtils.isEmpty(filters)) return;

        for(Filter filter : filters){
            filter.doFilter(resource,configuration);
        }
    }

    /**
     * 文件上传
     */
    protected abstract AccessPath doProcess(UploadResource resource, Configuration configuration) throws IOException;

}
