package com.iteaj.util.spring;

import com.iteaj.util.AssertUtils;
import com.iteaj.util.core.UtilsGlobalFactory;
import com.iteaj.util.core.UtilsType;
import com.iteaj.util.module.wechat.WechatTokenManager;
import com.iteaj.util.module.wechat.authhorize.WxcEnterpriseAuthorize;
import com.iteaj.util.module.wechat.authhorize.WxcWebAuthorize;
import com.iteaj.util.module.wechat.basictoken.BasicToken;
import com.iteaj.util.module.wechat.basictoken.WxcBasicToken;
import com.iteaj.util.module.wechat.basictoken.WxcEnterpriseBasicToken;
import com.iteaj.util.module.wechat.jsapi.WxcJsApi;
import com.iteaj.util.module.wechat.message.WxcTemplateMessage;

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

    @Override
    public void afterPropertiesSet() throws Exception {

        WechatTokenManager tokenManager = UtilsGlobalFactory.getWechatTokenManager();
        if(startServiceApi) {
            AssertUtils.isNotBlank(getAppId(), "未指定AppId", UtilsType.WECHAT);
            AssertUtils.isNotBlank(getAppSecret(), "未指定AppSecret", UtilsType.WECHAT);


            registerApi(new WxcBasicToken(getAppId(), getAppSecret()).buildApi());
            registerApi(new WxcTemplateMessage(getAppId(), getAppSecret(), tokenManager).buildApi());
            registerApi(new WxcWebAuthorize(getAppId(), getAppSecret(), getRedirectUrl()).buildApi());

            registerApi(new WxcJsApi(getAppId(), getAppSecret()).buildApi());

            //测试微信配置信息, 通过尝试获取token
            BasicToken basicToken = tokenManager.getToken(new WxcBasicToken(getAppId(), getAppSecret()));

            if(logger.isInfoEnabled()) {
                if(basicToken == null || !basicToken.success())
                    logger.warn("类别：微信服务号Api管理 - 动作：获取Token - 失败信息：{}", basicToken == null?null:basicToken.toString());
                else
                    logger.info("类别：微信服务号Api管理 - 动作：获取Token - Token信息：{}", basicToken.toString());
            }

        }

        if(startEnterpriseApi) {
            AssertUtils.isNotBlank(getCorpId(), "未指定CorpId", UtilsType.WECHAT);
            AssertUtils.isNotBlank(getAgentId(), "未指定AgentId", UtilsType.WECHAT);
            AssertUtils.isNotBlank(getCorpSecret(), "未指定CorpSecret", UtilsType.WECHAT);

            registerApi(new WxcEnterpriseAuthorize(getCorpId(), getCorpSecret(), getAgentId(), getRedirectUrl()).buildApi());

            //测试微信配置信息, 通过尝试获取token
            BasicToken basicToken = tokenManager.getToken(new WxcEnterpriseBasicToken(getCorpId(), getCorpSecret()));

            if(logger.isInfoEnabled()) {
                if(basicToken == null || !basicToken.success())
                    logger.warn("类别：微信企业号Api管理 - 动作：获取Token - 失败信息：{}", basicToken == null?null:basicToken.toString());
                else
                    logger.info("类别：微信企业号Api管理 - 动作：获取Token - Token信息：{}", basicToken.toString());
            }
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
