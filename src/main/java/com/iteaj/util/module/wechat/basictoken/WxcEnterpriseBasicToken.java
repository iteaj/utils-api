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
    public EnterpriseBasicTokenApi buildApi() {
        return new EnterpriseBasicTokenApi(this);
    }

    public class EnterpriseBasicTokenApi extends WechatBasicTokenApi {

        protected EnterpriseBasicTokenApi(WxcEnterpriseBasicToken config) {
            super(config);
        }

        @Override
        public BasicToken invoke(WxpBasicToken param) throws UtilsException {
            UrlBuilder urlBuilder = UrlBuilder.build(getApiGateway())
                    .addParam("corpid", getAppId())
                    .addParam("corpsecret", getAppSecret());
            String result = HttpUtils.doGet(urlBuilder, "UTF-8");
            if(CommonUtils.isBlank(result)) return null;

            return JsonUtils.toBean(result, BasicToken.class);
        }

        @Override
        public WechatApiType getApiType() {
            return WechatApiType.EnterpriseBasicToken;
        }
    }

}
