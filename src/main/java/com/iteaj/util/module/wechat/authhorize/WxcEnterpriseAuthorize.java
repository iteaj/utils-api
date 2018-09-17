package com.iteaj.util.module.wechat.authhorize;

import com.iteaj.util.AssertUtils;
import com.iteaj.util.core.UtilsType;

/**
 * create time: 2018/4/5
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class WxcEnterpriseAuthorize extends
        AbstractWechatOAuth2ApiConfig<WxaEnterpriseAuthorize> {

    private String state;
    private String userType;
    private String agentid;
    private String responseType;
    private String codeGateway;
    private String accessGateway;
    private String userInfoGateway;
    private String userDetailGateway;

    private String redirectUrl;


    public WxcEnterpriseAuthorize(String corpId, String corpSecret, String agentid) {
        this(corpId, corpSecret, agentid, null);
    }

    public WxcEnterpriseAuthorize(String corpId, String corpSecret
            , String agentId, String redirectUrl) {
        super(corpId, corpSecret, null);
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
    public WxaEnterpriseAuthorize buildApi() {
        AssertUtils.isNotBlank(getAppId(), "未指定CorpId", UtilsType.WECHAT);
        AssertUtils.isNotBlank(getAgentid(), "未指定AgentId", UtilsType.WECHAT);
        AssertUtils.isNotBlank(getAppSecret(), "未指定CorpSecret", UtilsType.WECHAT);
        return new WxaEnterpriseAuthorize(this).build();
    }

    @Override
    public String getApiGateway() {
        return this.userInfoGateway;
    }

    @Override
    public void setApiGateway(String gateway) {
        this.userInfoGateway = gateway;
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

    public WxcEnterpriseAuthorize setAgentid(String agentid) {
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

    public WxcEnterpriseAuthorize setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
        return this;
    }
}
