package com.iteaj.util.module.authorization.type;

import com.alibaba.fastjson.JSON;
import com.iteaj.util.module.authorization.*;
import com.iteaj.util.module.authorization.http.AsyncAuthorizationAbstract;
import com.iteaj.util.module.authorization.http.AuthorizePhaseAbstract;
import com.iteaj.util.module.authorization.http.SessionStorageContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * <p>微信网页授权</p>
 * Create Date By 2017-03-10
 * @author iteaj
 * @since 1.7
 */
public class WechatWebAuthorize extends
        AsyncAuthorizationAbstract<AsyncActionAbstract<WechatWebResult>> {
	
    private String lang;
    private String appId;
    private String state;
    private String scope;
    private String secret;
    private String grantType;
    private TypeEnum typeEnum;
    private String codeGateway;
    private String responseType;
    private String accessGateway;
    private String userInfoGateway;

    public WechatWebAuthorize() {
        this.state = "auth";
        this.lang = "zh_CN";
        this.responseType = "code";
        this.grantType = "authorization_code";
        typeEnum = TypeEnum.WechatUserInfo;
        this.userInfoGateway = "https://api.weixin.qq.com/sns/userinfo";
        this.accessGateway = "https://api.weixin.qq.com/sns/oauth2/access_token";
//        //本地
////        this.codeGateway = "https://open.weixin.qq.com/connect/oauth2/authorize";
//        //正式
        this.codeGateway = "https://open.weixin.qq.com/connect/oauth2/authorize";
//        //测试
////        this.codeGateway = "http://hcfront.1p1u.cn/get-weixin-code.html";
//        this.codeGateway = wechat_codeGateway;
    }

    @Override
    public void init() {
        super.init();
        //参数验证
        Assert.notNull(appId, "微信网页授权：没有设置 appid");
        Assert.notNull(scope, "微信网页授权：没有设置 scope");
        Assert.notNull(secret, "微信网页授权：没有设置 secret");

        //注册授权阶段
        UserPhase userPhase = new UserPhase(null);
        TokenPhase tokenPhase = new TokenPhase(userPhase);
        CodePhase codePhase = new CodePhase(tokenPhase);
        registerAuthorizePhase(new EntryPhase(codePhase));
        registerAuthorizePhase(codePhase);
        registerAuthorizePhase(tokenPhase);
        registerAuthorizePhase(userPhase);
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
        return "entry";
    }

    @Override
    public String getProcessStage() {
        return "entry-->code-->token-->user";
    }

    @Override
    public String getAsyncPhase() {
        return "user";
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
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

    public String getCodeGateway() {
        return codeGateway;
    }

    public void setCodeGateway(String codeGateway) {
        this.codeGateway = codeGateway;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getAccessGateway() {
        return accessGateway;
    }

    public void setAccessGateway(String accessGateway) {
        this.accessGateway = accessGateway;
    }

    public String getUserInfoGateway() {
        return userInfoGateway;
    }

    public void setUserInfoGateway(String userInfoGateway) {
        this.userInfoGateway = userInfoGateway;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    protected AuthorizeResult resolve(AuthorizeContext context) {
        return new WechatWebResult((SessionStorageContext) context);
    }

    /**
     * 微信access_token入口阶段
     */
    protected class EntryPhase extends AuthorizePhaseAbstract {

        private String html_pre = "<!DOCTYPE html><html lang=\"zh_cn\"><head><meta charset=\"UTF-8\"></head><body><a id=\"auto_submit\" href=\"";
        private String html_suf = "\" /><script>document.getElementById(\"auto_submit\").click();</script></body></html>";

        public EntryPhase(AuthorizePhase nextPhase) {
            super(nextPhase);
        }

        @Override
        public String phaseAlias() {
            return "entry";
        }

        @Override
        public void doPhase(PhaseChain chain, SessionStorageContext context) throws IOException {
            PrintWriter writer = null;
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(codeGateway).append("?appid=").append(appId)
                        .append("&redirect_uri=").append(getRedirectUrl(context))
                        .append("&response_type=").append(responseType)
                        .append("&scope=").append(scope)
                        .append("&connect_redirect=1#wechat_redirect");

                String html = html_pre + sb.toString() + html_suf;
                if(logger.isDebugEnabled()) {
                    logger.debug("响应Html：{}", html);
                }
//            chain.doPhase(context);
                writer = context.getResponse().getWriter();
                context.getResponse().setContentType("text/html; charset=utf-8");
                writer.print(html);
            } finally {
                writer.flush();
                writer.close();
            }
        }

        @Override
        public String getTypeAlias() {
            return WechatWebAuthorize.this.getTypeAlias();
        }
    }

    protected class CodePhase extends AuthorizePhaseAbstract {

        public CodePhase(AuthorizePhase nextPhase) {
            super(nextPhase);
        }

        @Override
        public String phaseAlias() {
            return "code";
        }

        @Override
        public void doPhase(PhaseChain chain, SessionStorageContext context) throws Exception {
            //获取微信授权code
            Object code = context.getParam("code");

            if(null == code) {
                logger.error(ERROR_INFO,getTypeAlias(), phaseAlias(), code);
                return;
            }

            //将返回的code存储到授权上下文
            context.addParam("code", code).addParam("state"
                    , context.getParam("state"));

            //执行下一阶段：token
            chain.doPhase(context);
        }

        @Override
        public String getTypeAlias() {
            return WechatWebAuthorize.this.getTypeAlias();
        }
    }

    protected class TokenPhase extends AuthorizePhaseAbstract {

        public TokenPhase(AuthorizePhase nextPhase) {
            super(nextPhase);
        }

        @Override
        public String phaseAlias() {
            return "token";
        }

        @Override
        public void doPhase(PhaseChain chain, SessionStorageContext context) throws Exception {
            StringBuilder sb = new StringBuilder(accessGateway).append("?")
                    .append("appid=").append(appId).append("&secret=").append(secret)
                    .append("&code=").append(context.getParam("code"))
                    .append("&grant_type=").append(grantType);
            String result = http.get(sb.toString());

            if(StringUtils.isBlank(result)) {
                logger.error(ERROR_INFO,getTypeAlias(), phaseAlias(), result);
                return;
            }

            //存储AccessToken信息到上下文
            WechatWebResult.AccessToken token = JSON
                    .parseObject(result, WechatWebResult.AccessToken.class);
            context.addParam(this.phaseAlias(), token);

            //执行下一阶段
            chain.doPhase(context);
        }

        @Override
        public String getTypeAlias() {
            return WechatWebAuthorize.this.getTypeAlias();
        }
    }

    protected class UserPhase extends AuthorizePhaseAbstract {

        public UserPhase(AuthorizePhase nextPhase) {
            super(nextPhase);
        }

        @Override
        public String phaseAlias() {
            return "user";
        }

        @Override
        public String getTypeAlias() {
            return WechatWebAuthorize.this.getTypeAlias();
        }

        @Override
        protected void doPhase(PhaseChain chain, SessionStorageContext context) throws Exception {
            WechatWebResult.AccessToken token = (WechatWebResult.AccessToken) context.getParam("token");
            StringBuilder sb = new StringBuilder(userInfoGateway).append("?")
                    .append("access_token=").append(token.getAccess_token())
                    .append("&openid=").append(token.getOpenid())
                    .append("&lang=").append(lang);
            String result = http.get(sb.toString());

            //获取用户信息失败
            if(StringUtils.isBlank(result)){
                logger.error(ERROR_INFO, getTypeAlias(), phaseAlias(), result);
                return;
            }

            //存储用户信息到上下文
            WechatWebResult.UserInfo userInfo = JSON
                    .parseObject(result, WechatWebResult.UserInfo.class);
            context.addParam(this.phaseAlias(), userInfo);
        }
    }
}
