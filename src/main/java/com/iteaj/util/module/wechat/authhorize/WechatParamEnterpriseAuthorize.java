package com.iteaj.util.module.wechat.authhorize;

import com.iteaj.util.AssertUtils;
import com.iteaj.util.core.UtilsType;
import com.iteaj.util.module.oauth2.OAuth2ApiParam;
import com.iteaj.util.module.wechat.WechatScope;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * create time: 2018/4/5
 *  微信企业号网页授权参数
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class WechatParamEnterpriseAuthorize extends OAuth2ApiParam {

    private WechatScope scope;

    public WechatParamEnterpriseAuthorize(HttpServletRequest request, HttpServletResponse response) {
        this(request, response, WechatScope.UserInfo);
    }

    public WechatParamEnterpriseAuthorize(HttpServletRequest request
            , HttpServletResponse response, WechatScope scope) {
        super(request, response);
        this.scope = scope;

        AssertUtils.isTrue(scope!=null,"请指定企业微信授权参数：scope", UtilsType.WECHAT);
    }

    /**
     *
     * @param request
     * @param response
     * @param redirectUrl 此参数和{@link WechatConfigEnterpriseAuthorize#redirectUrl}两者必填一处
     *                    , 如果两个地方都填,那么此处优先级更高
     */
    public WechatParamEnterpriseAuthorize(HttpServletRequest request
            , HttpServletResponse response, String redirectUrl) {
        super(request, response, redirectUrl);
    }

    /**
     *
     * @param request
     * @param response
     * @param redirectUrl 此参数和{@link WechatConfigEnterpriseAuthorize#redirectUrl}两者必填一处
     *                    , 如果两个地方都填,那么此处优先级更高
     * @param scope
     */
    public WechatParamEnterpriseAuthorize(HttpServletRequest request
            , HttpServletResponse response, String redirectUrl, WechatScope scope) {
        super(request, response, redirectUrl);
        this.scope = scope;
        AssertUtils.isTrue(scope!=null,"请指定企业微信授权参数：scope", UtilsType.WECHAT);
    }

    public WechatScope getScope() {
        return scope;
    }

    public WechatParamEnterpriseAuthorize setScope(WechatScope scope) {
        this.scope = scope;
        return this;
    }

    public WechatParamEnterpriseAuthorize setRedirectUrl(String redirectUrl) {
        super.setRedirectUrl(redirectUrl);
        return this;
    }

}
