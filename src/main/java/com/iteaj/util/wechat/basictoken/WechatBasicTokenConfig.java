package com.iteaj.util.wechat.basictoken;

import com.iteaj.util.AssertUtil;
import com.iteaj.util.UtilsType;
import com.iteaj.util.wechat.WechatConfig;

/**
 * Create Date By 2018-04-03
 *  获取微信普通的access_token的api需要使用的配置信息
 * @author iteaj
 * @since 1.7
 */
public class WechatBasicTokenConfig extends WechatConfig<WechatBasicToken> {

    /**普通的token使用client_credential*/
    private static String GRANT_TYPE = "client_credential";
    /**调用普通token需要的接口网关*/
    private static String TOKEN_GATEWAY = "https://api.weixin.qq.com/cgi-bin/token";

    public WechatBasicTokenConfig(String appId, String appSecret) {
        super(appId, appSecret);
    }

    @Override
    public WechatBasicToken buildApi() {
        AssertUtil.isNotBlank(getAppId(), "微信appId必填", UtilsType.WECHAT);
        AssertUtil.isNotBlank(getAppSecret(), "微信appSecret必填", UtilsType.WECHAT);
        return new WechatBasicToken(this);
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

    public String getTokenGateway() {
        return TOKEN_GATEWAY;
    }

    public void setTokenGateway(String tokenGateway) {
        this.TOKEN_GATEWAY = tokenGateway;
    }
}
