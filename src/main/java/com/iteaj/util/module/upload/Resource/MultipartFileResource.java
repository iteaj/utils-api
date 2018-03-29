package com.iteaj.util.module.upload.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * Create Date By 2016/8/29
 *  描述：springMvc提供的文件源
 *
 *  此文件源对应的处理器 {@link com.iteaj.util.module.upload.process.MvcResourceProcess}
 *
 * @author iteaj
 * @since 1.7
 */
public class MultipartFileResource implements UploadResource {

    private MultipartFile file;

    public MultipartFileResource(){

    }

    public MultipartFileResource(MultipartFile file){
        this.file = file;
    }

    @Override
    public String getFileName() {
        return file.getOriginalFilename();
    }

    @Override
    public String getContentType() {
        return file.getContentType();
    }

    @Override
    public long getSize() {
        return file.getSize();
    }

    @Override
    public String getFormName() {
        return null;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return file.getInputStream();
    }

    @Override
    public byte[] getByteArray() throws IOException {
        return file.getBytes();
    }

    @Override
    public boolean isEmpty() {
        return file.isEmpty();
    }

    @Override
    public String getSuffix() {
        String fileName = getFileName();
        if(StringUtils.isEmpty(fileName)){
        	return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }

    @Override
    public boolean isExist() {
        return (StringUtils.isNotBlank(getFileName()) || !isEmpty());
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
