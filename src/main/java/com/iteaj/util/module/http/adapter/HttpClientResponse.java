package com.iteaj.util.module.http.adapter;

import com.iteaj.util.CommonUtils;
import com.iteaj.util.core.UtilsException;
import com.iteaj.util.core.UtilsType;
import com.iteaj.util.module.http.HttpResponse;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;

/**
 * create time: 2018/4/1
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class HttpClientResponse implements HttpResponse {

    private byte[] content;
    private StatusLine statusLine;
    private CloseableHttpResponse response;

    public HttpClientResponse(CloseableHttpResponse response) {
        this.response = response;
        this.statusLine = response.getStatusLine();
    }

    @Override
    public int getStatus() {
        return statusLine.getStatusCode();
    }

    @Override
    public boolean success() {
        return statusLine.getStatusCode() == 200;
    }

    @Override
    public String getMessage() {
        return statusLine.getReasonPhrase();
    }

    @Override
    public byte[] getContent() {
        if(CommonUtils.isNotEmpty(content))
            return content;

        try {
            HttpEntity entity = response.getEntity();
            if(entity == null) return null;

            InputStream inputStream = entity.getContent();
            content = CommonUtils.read(inputStream);

            return content;
        } catch (IOException e) {
            throw new UtilsException("读取响应流失败", e, UtilsType.HTTP);
        }
    }

    @Override
    public String getContent(String charset) {
        try {
            if(CommonUtils.isNotEmpty(getContent()))
                return new String(content, charset);

            return null;
        } catch (Exception e) {
            throw new UtilsException("不支持的编码", e, UtilsType.HTTP);
        }
    }

    @Override
    public String getHeader(String name) {
        return response.getHeaders(name).toString();
    }

    @Override
    public String getContentEncoding() {
        return response.getHeaders("Content-Encoding").toString();
    }

    @Override
    public void close() throws IOException {
        response.close();
    }
}
