package com.iteaj.util.module.wechat.basictoken;

import com.iteaj.util.CommonUtils;
import com.iteaj.util.HttpUtils;
import com.iteaj.util.JsonUtils;
import com.iteaj.util.module.http.build.UrlBuilder;
import com.iteaj.util.module.wechat.AbstractWechatApi;
import com.iteaj.util.module.wechat.WechatApiType;

/**
 * <p>微信access_token</p>
 * Create Date By 2017-03-07
 * @author iteaj
 * @since 1.7
 */
public class WxaBasicToken extends AbstractWechatApi
        <WxcBasicToken, WxpBasicToken> {

    protected WxaBasicToken(WxcBasicToken config) {
        super(config);
    }

    /**
     * 向微信发起一次获取Access_Token的接口调用
     * 注：此接口无需参数 所以 param 直接指定为Null
     * @param param 此参数请设置为Null
     * @return
     */
    @Override
    public BasicToken invoke(WxpBasicToken param) {
        //调用微信接口并且获取调用结果
        UrlBuilder builder = UrlBuilder.build(getApiConfig().getApiGateway())
                .addParam("appid", getApiConfig().getAppId())
                .addParam("secret", getApiConfig().getAppSecret())
                .addParam("grant_type", getApiConfig().getGrantType());

        String result = HttpUtils.doGet(builder, "utf-8");
        if(CommonUtils.isBlank(result)) {
            logger.warn("类别：微信Api - 动作：获取Token - 信息：获取失败, {}"
                    , getApiConfig().warn());

            return BasicToken.ErrBasicToken;
        }

        return JsonUtils.toBean(result, BasicToken.class);
    }

    @Override
    public WechatApiType getApiType() {
        return WechatApiType.BasicToken;
    }
}
