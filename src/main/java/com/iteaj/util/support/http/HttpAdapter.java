package com.iteaj.util.support.http;

import java.net.URL;

/**
 * create time: 2018/3/27
 *
 * @author iteaj
 * @version 1.0
 * @since 1.7
 */
public interface HttpAdapter {

    String get(String url);

    byte[] get(URL url);

    byte[] post();
}
