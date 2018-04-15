package com.iteaj.util.module.oauth2;

import com.iteaj.util.core.http.HttpApiConfig;

/**
 * create time: 2018/4/5
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public interface OAuth2ApiConfig extends HttpApiConfig {

    /**
     * 返回一个授权之后的重定向Url
     * @return
     */
    String getRedirectUrl();

}
