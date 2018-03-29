package com.iteaj.util.module.upload.process;

import com.iteaj.util.Generator;
import com.iteaj.util.module.upload.AccessPath;
import com.iteaj.util.module.upload.Configuration;
import com.iteaj.util.module.upload.Resource.UploadResource;
import com.iteaj.util.module.upload.ResourceProcess;
import com.iteaj.util.module.upload.transfer.TransferTarget;

import java.io.File;
import java.io.IOException;

/**
 * Create Date By 2016/8/29
 *  普通文件上传实现
 * @author iteaj
 * @since 1.7
 */
public class MvcResourceProcess extends ResourceProcess {

    /**如果上传目录不存在是否创建*/
    private boolean isCreate;
    /**上传目录是否存在*/
    private boolean isExists;
    /**文件名生成器(默认UUID生成器)*/
    private Generator generator;
    /**文件上传后的输出目标(默认服务器本地)*/
    private TransferTarget target;

    public MvcResourceProcess(Configuration configuration) {
        super(configuration);
        this.target = configuration.getTarget();
        this.isCreate = configuration.isCreate();
        this.isExists = configuration.isExists();
        this.generator = configuration.getGenerator();
    }

    @Override
    public AccessPath doProcess(UploadResource resource, Configuration configuration) throws IOException {
        String fileName = this.generator.Generator();
        File desc = new File(configuration.getRoot()+"/"+fileName+resource.getSuffix());
        target.transferTo(resource, desc);

        AccessPath accessPath = new AccessPath();
        accessPath.setAccessPath(configuration.getPrefix()+fileName+resource.getSuffix());
        return accessPath;
    }

    public Generator getGenerator() {
        return generator;
    }

    public void setGenerator(Generator generator) {
        this.generator = generator;
    }

    public boolean isCreate() {
        return isCreate;
    }

    public void setCreate(boolean create) {
        isCreate = create;
    }

    public TransferTarget getTarget() {
        return target;
    }

    public void setTarget(TransferTarget target) {
        this.target = target;
    }
}
