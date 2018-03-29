package com.iteaj.util.module.upload.transfer;

import com.iteaj.util.module.upload.Resource.UploadResource;
import com.iteaj.util.module.upload.utils.SpringFileCopyAdapter;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Create Date By 2016/8/29
 *  本地传输目标(将文件存储到本地服务器)
 * @author iteaj
 * @since 1.7
 */
public class LocationTarget extends TransferTarget {

    @Override
    public void transferTo(UploadResource resource, File desc) throws IOException {
        SpringFileCopyAdapter.copy(resource.getInputStream(), desc);
    }

    @Override
    public void transferTo(UploadResource resource, OutputStream outputStream) throws IOException {
        SpringFileCopyAdapter.copy(resource.getInputStream(), outputStream);
    }
}
