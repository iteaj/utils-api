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
public class WxcBasicToken extends WechatConfig<WechatBasicTokenApi> {

    /**普通的token使用client_credential*/
    private String GRANT_TYPE = "client_credential";

    public WxcBasicToken(String appId, String appSecret) {
        this(appId, appSecret, "https://api.weixin.qq.com/cgi-bin/token");
    }

    protected WxcBasicToken(String appId, String appSecret, String apiGateway) {
        super(appId, appSecret, apiGateway);
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

}
