package com.iteaj.util.wechat.authhorize;

import com.iteaj.util.module.oauth2.OAuth2ApiConfig;
import com.iteaj.util.wechat.WechatConfig;

/**
 * create time: 2018/4/5
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class WechatWebAuthorizeConfig extends WechatConfig<WechatWebAuthorizeApi>
        implements OAuth2ApiConfig {

    private String lang;
    private String scope;
    private String state;
    private String grantType;
    private String codeGateway;
    private String responseType;
    private String accessGateway;
    private String apiGateway;

    private String redirectUrl;

    public WechatWebAuthorizeConfig(String appId, String appSecret, String redirectUrl) {
        super(appId, appSecret);
        this.state = "auth";
        this.lang = "zh_CN";
        this.responseType = "code";
        this.scope = "snsapi_userinfo";
        this.redirectUrl = redirectUrl;
        this.grantType = "authorization_code";
        this.apiGateway = "https://api.weixin.qq.com/sns/userinfo";
        this.accessGateway = "https://api.weixin.qq.com/sns/oauth2/access_token";
        this.codeGateway = "https://open.weixin.qq.com/connect/oauth2/authorize";
    }

    @Override
    public WechatWebAuthorizeApi buildApi() {
        return new WechatWebAuthorizeApi(this).build();
    }

    @Override
    public String getApiGateway() {
        return this.apiGateway;
    }

    @Override
    public void setApiGateway(String gateway) {
        this.apiGateway = gateway;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getCodeGateway() {
        return codeGateway;
    }

    public void setCodeGateway(String codeGateway) {
        this.codeGateway = codeGateway;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getAccessGateway() {
        return accessGateway;
    }

    public void setAccessGateway(String accessGateway) {
        this.accessGateway = accessGateway;
    }

    @Override
    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
