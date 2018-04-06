package com.iteaj.util;

import java.nio.charset.Charset;

/**
 * create time: 2018/3/31
 *
 * @author iteaj
 * @version 1.0
 * @since 1.7
 */
public interface Const {

    Charset UTF_8 = Charset.forName("UTF-8");
    Charset ISO_8859_1 = Charset.forName("ISO-8859-1");

    String FILE_XML = ".xml";
    String FILE_HTML = ".html";

    /**
     * Url参数Key
     */
    String CONTEXT_PARAM_KEY = "utils_oauth2";
}
