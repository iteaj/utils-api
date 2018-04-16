package com.iteaj.util.module.oauth2;

import com.iteaj.util.AssertUtils;
import com.iteaj.util.core.ApiParam;
import com.iteaj.util.core.UtilsType;

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

    private String redirectUrl;

    public OAuth2ApiParam(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    public OAuth2ApiParam(HttpServletRequest request, HttpServletResponse response, String redirectUrl) {
        super(request, response);
        this.redirectUrl = redirectUrl;
        AssertUtils.isNotBlank(redirectUrl, "请指定OAuth2所需的RedirectUrl", UtilsType.OAuth2);
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public OAuth2ApiParam setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
        return this;
    }
}
