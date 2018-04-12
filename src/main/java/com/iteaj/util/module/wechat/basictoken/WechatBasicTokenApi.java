package com.iteaj.util.module.wechat.basictoken;

import com.iteaj.util.HttpUtils;
import com.iteaj.util.JsonUtils;
import com.iteaj.util.module.http.build.UrlBuilder;
import com.iteaj.util.module.wechat.AbstractWechatApi;
import com.iteaj.util.module.wechat.WechatApiParam;

/**
 * <p>微信access_token</p>
 * Create Date By 2017-03-07
 * @author iteaj
 * @since 1.7
 */
public class WechatBasicTokenApi extends AbstractWechatApi
        <WechatBasicTokenApiConfig, WechatApiParam> {

    private WechatBasicTokenApiConfig config;

    protected WechatBasicTokenApi(WechatBasicTokenApiConfig config) {
        this.config = config;
    }

    @Override
    public WechatBasicTokenApiConfig getApiConfig() {
        return config;
    }

    @Override
    public void setApiConfig(WechatBasicTokenApiConfig config) {
        this.config = config;
    }

    /**
     * 向微信发起一次获取Access_Token的接口调用
     * 注：此接口无需参数 所以 param 直接指定为Null
     * @param param 此参数请设置为Null
     * @return
     */
    @Override
    public BasicToken invoke(WechatApiParam param) {
        //调用微信接口并且获取调用结果
        UrlBuilder builder = UrlBuilder.build(getApiConfig().getApiGateway())
                .addParam("appid", getApiConfig().getAppId())
                .addParam("secret", getApiConfig().getAppSecret())
                .addParam("grant_type", getApiConfig().getGrantType());

        String result = HttpUtils.doGet(builder, "utf-8");
        return JsonUtils.toBean(result, BasicToken.class);
    }

    public static class BasicToken{

        private String errcode;
        private String errmsg;
        private String expires_in;
        private String access_token;

        public BasicToken(){

        }

        public boolean success(){
            return null == errcode;
        }

        /**
         * access_token过期时间
         * @return
         */
        public String getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(String expires_in) {
            this.expires_in = expires_in;
        }

        /**
         * 微信访问令牌
         * @return
         */
        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getErrcode() {
            return errcode;
        }

        public void setErrcode(String errcode) {
            this.errcode = errcode;
        }

        public String getErrmsg() {
            return errmsg;
        }

        public void setErrmsg(String errmsg) {
            this.errmsg = errmsg;
        }

        @Override
        public String toString() {
            return "BasicToken{" +
                    "errcode='" + errcode + '\'' +
                    ", errmsg='" + errmsg + '\'' +
                    ", expires_in='" + expires_in + '\'' +
                    ", access_token='" + access_token + '\'' +
                    '}';
        }
    }
}
