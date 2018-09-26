package com.iteaj.util.module.wechat;

import com.iteaj.util.AssertUtils;
import com.iteaj.util.core.UtilsType;
import com.iteaj.util.core.http.HttpApiConfig;

/**
 * Create Date By 2018-04-03
 *  微信各个接口的配置的基类
 * @author iteaj
 * @since 1.7
 */
public abstract class WechatConfig<A extends WechatApi> implements HttpApiConfig {

    /**微信用来标识唯一的一个App, 由微信提供*/
    private String appId;
    /**每个App的安全Key, 由微信提供*/
    private String appSecret;
    private String apiGateway;

    public WechatConfig(String appId, String apiGateway) {
        this(appId, null, apiGateway);
    }

    public WechatConfig(String appId, String appSecret, String apiGateway) {
        this.appId = appId;
        this.appSecret = appSecret;
        this.apiGateway = apiGateway;
        AssertUtils.isNotBlank(appId, "微信配置 - 未指定appId", UtilsType.WECHAT);
        AssertUtils.isNotBlank(appSecret, "微信配置 - 未指定appSecret", UtilsType.WECHAT);
    }

    /**
     * 通过微信的配置信息创建一个微信Api
     * @see WechatApi
     * @return
     */
    public abstract A buildApi();

    @Override
    public UtilsType getUtilsType() {
        return UtilsType.WECHAT;
    }

    /**
     * 微信用来标识唯一的一个App, 由微信提供
     * @return
     */
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * 每个App的安全Key, 由微信提供
     * @return
     */
    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    @Override
    public String getApiGateway() {
        return apiGateway;
    }

    @Override
    public void setApiGateway(String gateway) {
        this.apiGateway = gateway;
    }

    @Override
    public String warn() {
        return "请查看微信开发文档：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1445241432";
    }
}
