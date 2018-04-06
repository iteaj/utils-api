package com.iteaj.util.module.wechat.authhorize;

import com.iteaj.util.module.oauth2.OAuth2ApiParam;

import javax.servlet.http.HttpServletResponse;

/**
 * create time: 2018/4/5
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class WechatEnterpriseAuthorizeParam extends OAuth2ApiParam {

    public WechatEnterpriseAuthorizeParam(HttpServletResponse response) {
        super(response);
    }
}
