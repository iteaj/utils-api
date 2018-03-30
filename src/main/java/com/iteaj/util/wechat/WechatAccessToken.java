package com.iteaj.util.wechat;

import com.alibaba.fastjson.JSON;
import com.iteaj.util.http.param.UrlBuilder;
import com.iteaj.util.module.authorization.*;
import com.iteaj.util.module.authorization.type.TypeEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import java.nio.charset.Charset;

/**
 * <p>微信授权获取access_token</p>
 * Create Date By 2017-03-07
 * @author iteaj
 * @since 1.7
 */
public class WechatAccessToken extends SyncAuthorizeType<WechatAccessToken.BasicToken> {

    private String appId;
    private String secret;
    private String grantType;
    private TypeEnum typeEnum;
    private String accessGateway;
    private long expiresIn; //过期时间跨度

    public WechatAccessToken() {
        this.expiresIn = 7200; //默认7200秒
        this.grantType = "client_credential";
        this.accessGateway = "https://api.weixin.qq.com/cgi-bin/token";
        typeEnum = TypeEnum.WechatAccessToken;
    }

    @Override
    public void init() {
        Assert.notNull(appId, "微信获取普通access_token：没有设置 appid");
        Assert.notNull(secret, "微信获取普通access_token：没有设置 secret");
        registerAuthorizePhase(new TokenPhase());
    }

    @Override
    protected AuthorizeResult resolve(AuthorizeContext context) {
        return (BasicToken)context.getParam("token");
    }

    @Override
    public String getTypeAlias() {
        return typeEnum.name();
    }

    @Override
    public String getDescription() {
        return typeEnum.description;
    }

    @Override
    public String getPhaseEntry() {
        return "token";
    }

    @Override
    public String getProcessStage() {
        return "token";
    }

    @Override
    public String getAsyncPhase() {
        return "token";
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAccessGateway() {
        return accessGateway;
    }

    public void setAccessGateway(String accessGateway) {
        this.accessGateway = accessGateway;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    class TokenPhase implements AuthorizePhase<MapStorageContext> {

        private BasicToken basicToken;
        private long prevTime; //上一次调用接口时间

        @Override
        public String phaseAlias() {
            return "token";
        }

        @Override
        public AuthorizePhase nextPhase() {
            return null;
        }

        @Override
        public String getTypeAlias() {
            return WechatAccessToken.this.getTypeAlias();
        }

        @Override
        public void phase(PhaseChain chain, MapStorageContext context) throws Exception {
            long currentTime = System.currentTimeMillis();

            //验证时间是否过期
            boolean expires = basicToken==null || (currentTime- prevTime)/1000 > expiresIn;
            if(logger.isDebugEnabled())
                logger.debug("类别：微信接口 - 动作：获取AccessToken - 描述：获取普通的AccessToken - 过期/剩余/命中：{}(s)/{}(s)/{} "
                        , expiresIn, expiresIn - (currentTime - prevTime)/1000, !expires);

            if(!expires){
                //替换存储上下文为当前
                basicToken.setContext(context);
                context.addParam("token", basicToken);

            } else {

                //调用微信接口并且获取调用结果
                BasicToken basicToken = getBasicToken();

                //注入存储上下文
                basicToken.setContext(context);

                //处理时间过期,如果调用成功则当前时间从tempTime开始算
                if(basicToken.success()) {
                    prevTime = currentTime;
                    //如果过期时间比微信指定的还大则转成微信的过期时间
                    Integer wechatExpires = Integer.valueOf(basicToken.expires_in);
                    if(expiresIn > wechatExpires) expiresIn = wechatExpires;

                    this.basicToken = basicToken;
                }

                if(logger.isDebugEnabled())
                    logger.debug("类别：微信接口 - 动作：获取AccessToken - 描述：微信接口响应 - 响应状态：{} - 错误消息：{}"
                            , basicToken.success(), basicToken.errmsg);

                context.addParam("token", basicToken);
            }
//            chain.doPhase(context);
        }

        private BasicToken getBasicToken() throws Exception {

            UrlBuilder builder = UrlBuilder.build(accessGateway).addParam("grant_type", grantType);
            String result = http.get(builder.addParam("appid", appId).addParam("secret", secret));

            if (StringUtils.isBlank(result)) {
                throw new IllegalStateException("获取access_token失败,授权别名：" + getTypeAlias());
            }

            return JSON.parseObject(result, BasicToken.class);
        }

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
