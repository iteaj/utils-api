package com.iteaj.util.module.wechat.authhorize;

import com.iteaj.util.AssertUtils;
import com.iteaj.util.core.UtilsType;
import com.iteaj.util.module.oauth2.OAuth2ApiParam;
import com.iteaj.util.module.wechat.WechatScope;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * create time: 2018/4/5
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class WechatEnterpriseAuthorizeParam extends OAuth2ApiParam {

    private WechatScope scope;
    public WechatEnterpriseAuthorizeParam(HttpServletRequest request, HttpServletResponse response) {
        this(request, response, WechatScope.UserInfo);
    }

    public WechatEnterpriseAuthorizeParam(HttpServletRequest request
            , HttpServletResponse response, WechatScope scope) {
        super(request, response);
        this.scope = scope;

        AssertUtils.isTrue(scope!=null,"请指定授权域", UtilsType.WECHAT);
    }

    public WechatScope getScope() {
        return scope;
    }
}
