package com.iteaj.util.module.authorization.type;

import com.alibaba.fastjson.JSON;
import com.iteaj.util.module.authorization.*;
import com.iteaj.util.module.authorization.http.AuthorizePhaseAbstract;
import com.iteaj.util.module.authorization.http.SessionStorageContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;


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


    public WechatAccessToken() {
        this.grantType = "client_credential";
        this.accessGateway = "https://api.weixin.qq.com/cgi-bin/token";
        typeEnum = TypeEnum.WechatAccessToken;
    }

    @Override
    public void init() {
        super.init();
        Assert.notNull(appId, "微信获取普通access_token：没有设置 appid");
        Assert.notNull(secret, "微信获取普通access_token：没有设置 secret");
        registerAuthorizePhase(new TokenPhase(null));
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

    class TokenPhase extends AuthorizePhaseAbstract {

        public TokenPhase(AuthorizePhase nextPhase) {
            super(nextPhase);
        }

        @Override
        public String phaseAlias() {
            return "token";
        }

        @Override
        public String getTypeAlias() {
            return WechatAccessToken.this.getTypeAlias();
        }

        @Override
        protected void doPhase(PhaseChain chain, SessionStorageContext context) throws Exception {
            String url = accessGateway + "?grant_type="+grantType+"&appid="+appId+"&secret="+secret;
            String result = http.get(url);
            if(StringUtils.isBlank(result)) {
                logger.error("通过授权类型[{}]获取[access_token]失败：[{}]", getTypeAlias(), result);
                return;
            }
            BasicToken basicToken = JSON.parseObject(result, BasicToken.class);
            basicToken.setContext(context);
            context.addParam("token", basicToken);
        }
    }

    public static class BasicToken extends SyncResult {

        private String expires_in;
        private String access_token;

        public BasicToken(){

        }

        public BasicToken(AuthorizeContext context) {
            super(context);
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

        @Override
        public String toString() {
            return "BasicToken{" +
                    "expires_in='" + expires_in + '\'' +
                    ", access_token='" + access_token + '\'' +
                    '}';
        }
    }
}
