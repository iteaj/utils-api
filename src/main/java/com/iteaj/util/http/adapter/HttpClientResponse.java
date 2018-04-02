package com.iteaj.util.http.adapter;

import com.iteaj.util.http.HttpResponse;

import java.io.IOException;

/**
 * create time: 2018/4/1
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class HttpClientResponse implements HttpResponse {
    @Override
    public int getStatus() {
        return 0;
    }

    @Override
    public boolean success() {
        return false;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public byte[] getContent() {
        return new byte[0];
    }

    @Override
    public String getContent(String charset) {
        return null;
    }

    @Override
    public String getHeader(String name) {
        return null;
    }

    @Override
    public String getContentEncoding() {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}
