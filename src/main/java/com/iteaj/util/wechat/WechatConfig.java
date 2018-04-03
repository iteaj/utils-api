package com.iteaj.util.wechat;

import com.iteaj.util.ApiConfig;
import com.iteaj.util.AssertUtil;
import com.iteaj.util.UtilsType;

/**
 * Create Date By 2018-04-03
 *  微信各个接口的配置的基类
 * @author iteaj
 * @since 1.7
 */
public abstract class WechatConfig<A extends WechatApi> implements ApiConfig {

    /**微信用来标识唯一的一个App, 由微信提供*/
    private String appId;
    /**每个App的安全Key, 由微信提供*/
    private String appSecret;

    public WechatConfig(String appId) {
        this.appId = appId;
        AssertUtil.isNotBlank(appId, "微信配置 - 未指定appId", UtilsType.WECHAT);
    }

    public WechatConfig(String appId, String appSecret) {
        this.appId = appId;
        this.appSecret = appSecret;
        AssertUtil.isNotBlank(appId, "微信配置 - 未指定appId", UtilsType.WECHAT);
        AssertUtil.isNotBlank(appSecret, "微信配置 - 未指定appSecret", UtilsType.WECHAT);
    }

    public abstract A buildApi();

    @Override
    public UtilsType getType() {
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
}
