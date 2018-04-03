package com.iteaj.util.wechat.basictoken;

import com.iteaj.util.HttpUtils;
import com.iteaj.util.JsonUtils;
import com.iteaj.util.UtilsApi;
import com.iteaj.util.http.build.UrlBuilder;
import com.iteaj.util.module.authorization.AuthorizeContext;
import com.iteaj.util.module.authorization.SyncResult;
import com.iteaj.util.wechat.WechatApi;

/**
 * <p>微信授权获取access_token</p>
 * Create Date By 2017-03-07
 * @author iteaj
 * @since 1.7
 */
public class WechatBasicToken implements WechatApi<WechatBasicTokenConfig, UtilsApi.VoidApiParam> {

    private WechatBasicTokenConfig config;

    protected WechatBasicToken(WechatBasicTokenConfig config) {
        this.config = config;
    }

    @Override
    public WechatBasicTokenConfig getConfig() {
        return config;
    }

    @Override
    public void setConfig(WechatBasicTokenConfig config) {
        this.config = config;
    }

    /**
     * 向微信发起一次获取Access_Token的接口调用
     * @param param 调用所需的参数
     * @return
     */
    @Override
    public BasicToken invoke(VoidApiParam param) {
        //调用微信接口并且获取调用结果
        return getBasicToken();
    }


    private BasicToken getBasicToken() {

        UrlBuilder builder = UrlBuilder.build(getConfig().getApiGateway())
                .addParam("appid", getConfig().getAppId())
                .addParam("secret", getConfig().getAppSecret())
                .addParam("grant_type", getConfig().getGrantType());

        String result = HttpUtils.doGet(builder, "utf-8");
        return JsonUtils.toBean(result, BasicToken.class);
    }

    public static class BasicToken extends SyncResult{

        private String errcode;
        private String errmsg;
        private String expires_in;
        private String access_token;

        public BasicToken(){

        }

        public BasicToken(AuthorizeContext context) {
            super(context);
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
