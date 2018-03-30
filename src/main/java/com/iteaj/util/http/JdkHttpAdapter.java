package com.iteaj.util.http;

import com.iteaj.util.UtilsException;
import com.iteaj.util.UtilsType;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

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
    public String get(String url, String charset) throws UtilsException {
        return null;
    }

    @Override
    public byte[] get(String url, String... values) throws UtilsException {
        return new byte[0];
    }

    @Override
    public String get(String url, Charset charset, String... values) throws UtilsException {
        return null;
    }

    @Override
    public byte[] post(URL url, Charset charset, String... values) throws UtilsException {
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

            return connection;
        } catch (IOException e) {
            throw new UtilsException("url连接不可用", e, UtilsType.HTTP);
        }
    }
}
