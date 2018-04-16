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
public class WechatParamWebAuthorize extends OAuth2ApiParam {

    private WechatScope scope; //授权域 有两种值：1.snsapi_userinfo 2.snsapi_base

    public WechatParamWebAuthorize(HttpServletRequest request, HttpServletResponse response) {
        this(request, response, WechatScope.UserInfo);
    }

    public WechatParamWebAuthorize(HttpServletRequest request
            , HttpServletResponse response, WechatScope scope) {
        super(request, response);
        this.scope = scope;
        AssertUtils.isTrue(scope!=null,"请指定授权域",UtilsType.WECHAT);
    }

    /**
     *
     * @param request
     * @param response
     * @param redirectUrl 此参数和{@link WechatConfigWebAuthorize#redirectUrl}两者必填一处
     *                    , 如果两个地方都填,那么此处优先级更高
     */
    public WechatParamWebAuthorize(HttpServletRequest request
            , HttpServletResponse response, String redirectUrl) {
        super(request, response, redirectUrl);
    }

    /**
     *
     * @param request
     * @param response
     * @param redirectUrl 此参数和{@link WechatConfigWebAuthorize#redirectUrl}两者必填一处
     *                    , 如果两个地方都填,那么此处优先级更高
     * @param scope
     */
    public WechatParamWebAuthorize(HttpServletRequest request
            , HttpServletResponse response, String redirectUrl, WechatScope scope) {
        super(request, response, redirectUrl);
        this.scope = scope;
    }

    public WechatScope getScope() {
        return scope;
    }

    public void setScope(WechatScope scope) {
        this.scope = scope;
    }
}
