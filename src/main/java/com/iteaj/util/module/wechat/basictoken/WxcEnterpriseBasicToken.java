package com.iteaj.util.module.wechat.basictoken;

import com.iteaj.util.CommonUtils;
import com.iteaj.util.HttpUtils;
import com.iteaj.util.JsonUtils;
import com.iteaj.util.core.UtilsException;
import com.iteaj.util.module.http.build.UrlBuilder;
import com.iteaj.util.module.wechat.WechatApiType;

/**
 * create time: 2018/4/14
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class WxcEnterpriseBasicToken extends WxcBasicToken {

    public WxcEnterpriseBasicToken(String appId, String appSecret) {
        super(appId, appSecret, "https://qyapi.weixin.qq.com/cgi-bin/gettoken");
    }

    @Override
    public WxaEnterpriseBasicToken buildApi() {
        return new WxaEnterpriseBasicToken(this);
    }

    public class WxaEnterpriseBasicToken extends WxaBasicToken {

        protected WxaEnterpriseBasicToken(WxcEnterpriseBasicToken config) {
            super(config);
        }

        @Override
        public BasicToken invoke(WxpBasicToken param) throws UtilsException {
            UrlBuilder urlBuilder = UrlBuilder.build(getApiGateway())
                    .addParam("corpid", getAppId())
                    .addParam("corpsecret", getAppSecret());

            String result = HttpUtils.doGet(urlBuilder, "UTF-8");
            if(CommonUtils.isBlank(result)) {
                logger.warn("类别：微信Api - 动作：获取企业号Token - 信息：获取失败, {}"
                        , getApiConfig().warn());

                return BasicToken.ErrBasicToken;
            }

            return JsonUtils.toBean(result, BasicToken.class);
        }

        @Override
        public WechatApiType getApiType() {
            return WechatApiType.EnterpriseBasicToken;
        }
    }

    @Override
    public String warn() {
        return "详情见：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140183";
    }
}
