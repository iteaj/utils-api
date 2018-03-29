package com.iteaj.util.module.upload;

import com.iteaj.util.module.upload.Resource.UploadResource;

/**
 * Create Date By 2016/8/30
 *  文件源处理器注册
 * @author iteaj
 * @since 1.7
 */
public interface ResourceProcessRegistry {

    void register(Class<? extends UploadResource> resource, ResourceProcess process);

    ResourceProcess getValue(Class<? extends UploadResource> resource);
}
