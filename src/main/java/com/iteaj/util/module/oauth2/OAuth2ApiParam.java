package com.iteaj.util.module.oauth2;

import com.iteaj.util.core.ApiParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * create time: 2018/4/5
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public abstract class OAuth2ApiParam extends AbstractStorageContext
        implements ApiParam<OAuth2AuthorizeApi.InvokeReturn> {


    public OAuth2ApiParam(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }
}
