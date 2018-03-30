package com.iteaj.util.http;

import com.iteaj.util.UtilsException;
import com.iteaj.util.UtilsType;
import com.iteaj.util.http.param.UrlBuilder;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.UUID;

/**
 * Create Date By 2018-03-30
 *
 * @author iteaj
 * @since 1.7
 */
public class JdkHttpAdapter implements HttpAdapter {

    /** boundary前缀 */
    private static String PREFIX = "---";
    /** 换车换行 */
    private static String ENTER = "\r\n";

    @Override
    public byte[] get(URL url) throws UtilsException {
        HttpURLConnection connection = getConnection(url);

        return new byte[0];
    }

    @Override
    public String get(UrlBuilder builder, String charset) throws UtilsException {
        return null;
    }

    @Override
    public String get(UrlBuilder builder) throws UtilsException {
        return null;
    }


    @Override
    public byte[] post(URL url, Charset charset, String... values) throws UtilsException {
        HttpURLConnection connection = getConnection(url);

        //创建随机分隔符
        String boundary = PREFIX+ UUID.randomUUID().toString();

        //设置http协议的请求头
        setHeaders(connection, boundary);

//        doConnect(connection, values);
        return new byte[0];
    }

    @Override
    public byte[] post(String url, byte[] body, String... values) throws UtilsException {
        return new byte[0];
    }

    @Override
    public String post(String url, Charset charset, String... values) throws UtilsException {
        return null;
    }

    @Override
    public byte[] post(String url, File[] files, String... values) throws UtilsException {
        return new byte[0];
    }

    protected HttpURLConnection getConnection(URL url) {
        try {
            if(null == url) throw new UtilsException("url连接不可用", UtilsType.HTTP);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setDoInput(true); //设置可输入
            connection.setDoOutput(true); //设置可输出

            connection.setRequestMethod("POST");
            return connection;
        } catch (IOException e) {
            throw new UtilsException("url连接不可用", e, UtilsType.HTTP);
        }
    }


    protected byte[] doConnect(HttpURLConnection connection) throws IOException {
        connection.connect();
        return null;
    }

    /**
     * 设置http头信息
     * @param connection
     * @param boundary
     */
    protected void setHeaders(HttpURLConnection connection, String boundary) {

        connection.setUseCaches(false);

        //设置成长连接
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setRequestProperty("Accept", "*/*");
        connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
        connection.setRequestProperty("Cache-Control", "no-cache");

        /**
         * 设置Content-Type类型
         * 1. multipart/form-data 声明是提交带有附件
         * 2. boundary 用来作为每个提交参数的分隔符
         */
        connection.setRequestProperty("Content-Type","multipart/form-data; " +
                "boundary=" + boundary);
    }

    /**
     * 解析参数
     * @param values
     * @return
     */
    protected Map<String, String> resolver(String... values) {

        return null;
    }

    public static void main(String[] args) {

        UrlBuilder builder = UrlBuilder.build("http://www.baidu.com");
        builder.addParam("test", "name");

        System.out.println(builder);
    }
}
