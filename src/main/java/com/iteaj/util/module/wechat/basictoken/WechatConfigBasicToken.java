package com.iteaj.util.module.wechat.basictoken;

import com.iteaj.util.AssertUtils;
import com.iteaj.util.core.UtilsType;
import com.iteaj.util.module.wechat.WechatConfig;

/**
 * Create Date By 2018-04-03
 *  获取微信普通的access_token的api需要使用的配置信息
 * @author iteaj
 * @since 1.7
 */
public class WechatConfigBasicToken extends WechatConfig<WechatBasicTokenApi> {

    /**普通的token使用client_credential*/
    private String GRANT_TYPE = "client_credential";
    /**调用普通token需要的接口网关*/
    private String TOKEN_GATEWAY = "https://api.weixin.qq.com/cgi-bin/token";

    public WechatConfigBasicToken(String appId, String appSecret) {
        super(appId, appSecret);
    }

    @Override
    public WechatBasicTokenApi buildApi() {
        AssertUtils.isNotBlank(getAppId(), "微信appId必填", UtilsType.WECHAT);
        AssertUtils.isNotBlank(getAppSecret(), "微信appSecret必填", UtilsType.WECHAT);
        return new WechatBasicTokenApi(this);
    }

    /**
     * 授权类型
     * 普通的token用client_credential
     * @return
     */
    public String getGrantType() {
        return GRANT_TYPE;
    }

    public void setGrantType(String grantType) {
        this.GRANT_TYPE = grantType;
    }

    @Override
    public String getApiGateway() {
        return TOKEN_GATEWAY;
    }

    @Override
    public void setApiGateway(String gateway) {
        TOKEN_GATEWAY = gateway;
    }
}
