package com.iteaj.util.module.oauth2;

import com.iteaj.util.UtilsApi;

import javax.servlet.http.HttpServletResponse;

/**
 * create time: 2018/4/5
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public abstract class OAuth2ApiParam
        extends AbstractStorageContext implements UtilsApi.ApiParam {

    public OAuth2ApiParam(HttpServletResponse response) {
        super(response);
    }
}
