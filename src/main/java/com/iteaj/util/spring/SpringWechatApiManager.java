package com.iteaj.util.spring;

import com.iteaj.util.AssertUtils;
import com.iteaj.util.core.UtilsGlobalDefaultFactory;
import com.iteaj.util.core.UtilsType;
import com.iteaj.util.module.wechat.WechatTokenManager;
import com.iteaj.util.module.wechat.authhorize.WechatConfigEnterpriseAuthorize;
import com.iteaj.util.module.wechat.authhorize.WechatConfigWebAuthorize;
import com.iteaj.util.module.wechat.basictoken.BasicToken;
import com.iteaj.util.module.wechat.basictoken.WechatConfigBasicToken;
import com.iteaj.util.module.wechat.basictoken.WechatConfigEnterpriseBasicToken;
import com.iteaj.util.module.wechat.message.WechatConfigTemplateMessage;
import com.iteaj.util.spring.SpringApiManager;

/**
 * create time: 2018/4/14
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class SpringWechatApiManager extends SpringApiManager {

    private String appId;
    private String appSecret;

    private String corpId;
    private String agentId;
    private String corpSecret;

    private String redirectUrl;
    private boolean startServiceApi;
    private boolean startEnterpriseApi;

    private WechatTokenManager tokenManager;
    @Override
    public void afterPropertiesSet() throws Exception {

        if(startServiceApi) {
            AssertUtils.isNotBlank(getAppId(), "未指定AppId", UtilsType.WECHAT);
            AssertUtils.isNotBlank(getAppSecret(), "未指定AppSecret", UtilsType.WECHAT);

            if(null == getTokenManager()) {
                //如果没有指定则用默认的本地token管理
                tokenManager = UtilsGlobalDefaultFactory.getTokenManager();
            }

            registerApi(new WechatConfigBasicToken(getAppId(), getAppSecret()).buildApi());
            registerApi(new WechatConfigTemplateMessage(getAppId(), getAppSecret(), tokenManager).buildApi());
            registerApi(new WechatConfigWebAuthorize(getAppId(), getAppSecret(), getRedirectUrl()).buildApi());

            //测试微信配置信息, 通过尝试获取token
            tokenManager.getToken(new WechatConfigBasicToken(getAppId(), getAppSecret()));
        }

        if(startEnterpriseApi) {
            AssertUtils.isNotBlank(getCorpId(), "未指定CorpId", UtilsType.WECHAT);
            AssertUtils.isNotBlank(getAgentId(), "未指定AgentId", UtilsType.WECHAT);
            AssertUtils.isNotBlank(getCorpSecret(), "未指定CorpSecret", UtilsType.WECHAT);

            registerApi(new WechatConfigEnterpriseAuthorize(getCorpId(), getCorpSecret(), getAgentId(), getRedirectUrl()).buildApi());

            //测试微信配置信息, 通过尝试获取token
            tokenManager.getToken(new WechatConfigEnterpriseBasicToken(getCorpId(), getCorpSecret()));
        }


    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getCorpSecret() {
        return corpSecret;
    }

    public void setCorpSecret(String corpSecret) {
        this.corpSecret = corpSecret;
    }

    public boolean isStartServiceApi() {
        return startServiceApi;
    }

    /**
     * 是否启用服务号(订阅号)相关的接口
     * @param startServiceApi
     */
    public void setStartServiceApi(boolean startServiceApi) {
        this.startServiceApi = startServiceApi;
    }

    public boolean isStartEnterpriseApi() {
        return startEnterpriseApi;
    }

    public WechatTokenManager getTokenManager() {
        return tokenManager;
    }

    public void setTokenManager(WechatTokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    /**
     * 是否启用企业号相关的接口
     * @param startEnterpriseApi
     */
    public void setStartEnterpriseApi(boolean startEnterpriseApi) {
        this.startEnterpriseApi = startEnterpriseApi;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
