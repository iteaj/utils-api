package com.iteaj.util.module.wechat.authhorize;

/**
 * create time: 2018/4/5
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class WechatEnterpriseAuthorizeConfig extends
        AbstractWechatOAuth2ApiConfig<WechatEnterpriseAuthorizeApi> {

    private String state;
    private String userType;
    private String agentid;
    private String responseType;
    private String codeGateway;
    private String accessGateway;
    private String userInfoGateway;
    private String userDetailGateway;

    private String redirectUrl;

    public WechatEnterpriseAuthorizeConfig(String corpId, String corpSecret
            , String agentId, String redirectUrl) {
        super(corpId, corpSecret);
        this.state = "auth";
        this.agentid = agentId;
        this.responseType = "code";
        this.redirectUrl = redirectUrl;
        this.accessGateway = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
        this.codeGateway = "https://open.weixin.qq.com/connect/oauth2/authorize";
        this.userInfoGateway = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo";
        this.userDetailGateway ="https://qyapi.weixin.qq.com/cgi-bin/user/getuserdetail";
    }

    @Override
    public WechatEnterpriseAuthorizeApi buildApi() {
        return new WechatEnterpriseAuthorizeApi(this).build();
    }

    @Override
    public String getApiGateway() {
        return null;
    }

    @Override
    public void setApiGateway(String gateway) {

    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAgentid() {
        return agentid;
    }

    public WechatEnterpriseAuthorizeConfig setAgentid(String agentid) {
        this.agentid = agentid;
        return this;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getCodeGateway() {
        return codeGateway;
    }

    public void setCodeGateway(String codeGateway) {
        this.codeGateway = codeGateway;
    }

    public String getAccessGateway() {
        return accessGateway;
    }

    public void setAccessGateway(String accessGateway) {
        this.accessGateway = accessGateway;
    }

    public String getUserInfoGateway() {
        return userInfoGateway;
    }

    public void setUserInfoGateway(String userInfoGateway) {
        this.userInfoGateway = userInfoGateway;
    }

    public String getUserDetailGateway() {
        return userDetailGateway;
    }

    public void setUserDetailGateway(String userDetailGateway) {
        this.userDetailGateway = userDetailGateway;
    }

    @Override
    public String getRedirectUrl() {
        return this.redirectUrl;
    }

    public WechatEnterpriseAuthorizeConfig setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
        return this;
    }
}
