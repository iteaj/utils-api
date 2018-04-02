package com.iteaj.util.http.adapter;

import com.iteaj.util.CommonUtils;
import com.iteaj.util.UtilsException;
import com.iteaj.util.UtilsType;
import com.iteaj.util.http.HttpResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create time: 2018/4/1
 *  对JDK 自带的Http的响应的简单封装
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class JdkHttpResponse implements HttpResponse {

    private int statusCode;
    private byte[] content;
    private String message;
    private HttpURLConnection connection;
    private Map<String, String> headers;
    private static Map<String, String> EMPTY = new HashMap<>(0);

    public JdkHttpResponse(int statusCode, byte[] content, HttpURLConnection connection) {
        try {
            this.content = content;
            this.statusCode = statusCode;
            this.connection = connection;
            this.headers = getHeaderFields(connection);
            this.message = connection.getResponseMessage();
        } catch (IOException e) {
            throw new UtilsException("Http-解析响应失败", e, UtilsType.HTTP);
        }
    }

    private Map<String, String> getHeaderFields(HttpURLConnection connection) {
        Map<String, List<String>> fields = connection.getHeaderFields();
        if(!CommonUtils.isNotEmpty(fields)) return EMPTY;

        headers = new HashMap<>(fields.size());
        for(String name : fields.keySet()) {
            List<String> values = fields.get(name);

            headers.put(name, CommonUtils.concat(values, "separator"));
        }
        return headers;
    }

    @Override
    public int getStatus() {
        return statusCode;
    }

    @Override
    public boolean success() {
        return getStatus() == 200;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public byte[] getContent() {

        return content;
    }

    @Override
    public String getContent(String charset) {
        try {
            if(CommonUtils.isNotEmpty(content))
                return new String(content, charset);

            return null;
        } catch (UnsupportedEncodingException e) {
            throw new UtilsException("Http-不支持的编码："+charset, e, UtilsType.HTTP);
        }
    }

    @Override
    public String getHeader(String name) {
        return headers.get(name);
    }

    @Override
    public String getContentEncoding() {
        return headers.get("Content-Encoding");
    }

    @Override
    public void close() {
        if(null != connection)
            connection.disconnect();
    }

    public HttpURLConnection getConnection() {
        return connection;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}
