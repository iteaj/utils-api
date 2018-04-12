package com.iteaj.util.module.wechat.authhorize;

import com.iteaj.util.HttpUtils;
import com.iteaj.util.JsonUtils;
import com.iteaj.util.module.http.build.UrlBuilder;
import com.iteaj.util.module.oauth2.*;
import com.iteaj.util.module.wechat.AbstractWechatPhase;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * <p>微信服务号和订阅号的网页授权接口Api</p>
 * Create Date By 2017-03-10
 * @author iteaj
 * @since 1.7
 */
public class WechatWebAuthorizeApi extends AbstractWechatOAuth2Api
        <WechatWebAuthorizeConfig, WechatWebAuthorizeParam>{
	
    public WechatWebAuthorizeApi(WechatWebAuthorizeConfig config) {
        super(config);
    }

    @Override
    public WechatWebAuthorizeApi build() {
        super.build();

        //注册授权阶段
        UserPhase userPhase = new UserPhase(null);
        TokenPhase tokenPhase = new TokenPhase(userPhase);
        CodePhase codePhase = new CodePhase(tokenPhase);
        registerAuthorizePhase(new EntryPhase(codePhase));
        registerAuthorizePhase(codePhase);
        registerAuthorizePhase(tokenPhase);
        registerAuthorizePhase(userPhase);

        return this;
    }

    @Override
    public Class<? extends AbstractAuthorizeResult> authorizeResult() {
        return WechatWebResult.class;
    }

    @Override
    public String getDescription() {
        return "微信服务号网页授权";
    }

    @Override
    public String getPhaseEntry() {
        return "entry";
    }

    @Override
    public String getProcessStage() {
        return "entry-->code-->token-->user";
    }

    /**
     * 微信access_token入口阶段
     */
    protected class EntryPhase extends AbstractWechatPhase<WechatWebAuthorizeParam> {

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
        public void doPhase(PhaseChain chain, WechatWebAuthorizeParam context) {
            PrintWriter writer = null;
            try {
                StringBuilder sb = new StringBuilder(html_pre);
                sb.append(getApiConfig().getCodeGateway())
                        .append("?appid=").append(getApiConfig().getAppId())
                        .append("&redirect_uri=").append(getRedirectUrl(context, getApiConfig().getRedirectUrl()))
                        .append("&response_type=").append(getApiConfig().getResponseType())
                        .append("&scope=").append(context.getScope().val)
                        .append("&state=").append(getApiConfig().getState()).append("#wechat_redirect");
                sb.append(html_suf);

                String html = sb.toString();
                if(logger.isDebugEnabled()) {
                    logger.debug("类别：微信Api - 动作：获取code - 描述：写html参数到微信客户端 [{}]", html);
                }

                writer = context.getResponse().getWriter();
                context.getResponse().setContentType("text/html; charset=utf-8");
                writer.print(html);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                writer.flush();
                writer.close();
            }
        }

        @Override
        public String getTypeAlias() {
            return WechatWebAuthorizeApi.this.getTypeAlias();
        }
    }

    protected class CodePhase extends AbstractWechatPhase<WechatWebAuthorizeParam> {

        public CodePhase(AuthorizePhase nextPhase) {
            super(nextPhase);
        }

        @Override
        public String phaseAlias() {
            return "code";
        }

        @Override
        public void doPhase(PhaseChain chain, WechatWebAuthorizeParam context) {
            //获取微信授权code
            String code = context.getRequest().getParameter("code");
            String state = context.getRequest().getParameter("state");

            if(!isSuccess(context, code, phaseAlias(), "获取code失败"))return;

            //将返回的code存储到授权上下文
            context.addContextParam("code", code).addContextParam("state", state);

            //执行下一阶段：token
            chain.doPhase(context);
        }

        @Override
        public String getTypeAlias() {
            return WechatWebAuthorizeApi.this.getTypeAlias();
        }
    }

    protected class TokenPhase extends AbstractWechatPhase<WechatWebAuthorizeParam> {

        public TokenPhase(AuthorizePhase nextPhase) {
            super(nextPhase);
        }

        @Override
        public String phaseAlias() {
            return "token";
        }

        @Override
        public void doPhase(PhaseChain chain, WechatWebAuthorizeParam context) {
            StringBuilder sb = new StringBuilder(getApiConfig().getAccessGateway()).append("?")
                    .append("appid=").append(getApiConfig().getAppId())
                    .append("&secret=").append(getApiConfig().getAppSecret())
                    .append("&code=").append(context.getContextParam("code"))
                    .append("&grant_type=").append(getApiConfig().getGrantType());
            String result = HttpUtils.doGet(UrlBuilder.build(sb.toString()), "UTF-8");

            if(!isSuccess(context, result, phaseAlias(), "获取Access_Token失败"))return;

            //存储AccessToken信息到上下文
            AccessToken token = JsonUtils.toBean(result, AccessToken.class);
            context.addContextParam(this.phaseAlias(), token);

            //执行下一阶段
            chain.doPhase(context);
        }

        @Override
        public String getTypeAlias() {
            return WechatWebAuthorizeApi.this.getTypeAlias();
        }
    }

    protected class UserPhase extends AbstractWechatPhase<WechatWebAuthorizeParam> {

        public UserPhase(AuthorizePhase nextPhase) {
            super(nextPhase);
        }

        @Override
        public String phaseAlias() {
            return "user";
        }

        @Override
        public String getTypeAlias() {
            return WechatWebAuthorizeApi.this.getTypeAlias();
        }

        @Override
        protected void doPhase(PhaseChain chain, WechatWebAuthorizeParam context) {
            AccessToken token = (AccessToken) context.getContextParam("token");
            StringBuilder sb = new StringBuilder(getApiConfig().getApiGateway()).append("?")
                    .append("access_token=").append(token.getAccess_token())
                    .append("&openid=").append(token.getOpenid())
                    .append("&lang=").append(getApiConfig().getLang());
            String result = HttpUtils.doGet(UrlBuilder.build(sb.toString()), "UTF-8");

            //获取用户信息失败
            if(!isSuccess(context, result, phaseAlias(), "获取用户信息失败"))return;

            //存储用户信息到上下文
            UserInfo userInfo = JsonUtils.toBean(result, UserInfo.class);
            context.addContextParam(this.phaseAlias(), userInfo);
        }
    }

    /**
     * Created by iteaj on 2017/3/14.
     */
    public static class WechatWebResult extends AbstractAuthorizeResult {

        private String code;
        private UserInfo userInfo;
        private AccessToken accessToken;

        public WechatWebResult(AbstractStorageContext context) {
            super(context);

        }

        @Override
        public void build() {
            this.code = (String)context.getContextParam("code");
            this.userInfo = (UserInfo) context.getContextParam("user");
            this.accessToken = (AccessToken) context.getContextParam("token");
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public UserInfo getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfo userInfo) {
            this.userInfo = userInfo;
        }

        public AccessToken getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(AccessToken accessToken) {
            this.accessToken = accessToken;
        }

    }

    /**
     * 微信网页授权返回的AccessToken对象
     */
    public static class AccessToken extends WechatResultEntityAbstract{
        private String scope;
        private String openid;
        private String expires_in;
        private String access_token;
        private String refresh_token;

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(String expires_in) {
            this.expires_in = expires_in;
        }

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getRefresh_token() {
            return refresh_token;
        }

        public void setRefresh_token(String refresh_token) {
            this.refresh_token = refresh_token;
        }

        @Override
        public String toString() {
            return "AccessToken{" +
                    "scope='" + scope + '\'' +
                    ", openid='" + openid + '\'' +
                    ", expires_in='" + expires_in + '\'' +
                    ", access_tToken='" + access_token + '\'' +
                    ", refresh_token='" + refresh_token + '\'' +
                    '}';
        }
    }

    /**
     * 微信返回的用户信息
     */
    public static class UserInfo extends WechatResultEntityAbstract{
        private String sex;
        private String city;
        private String openid;
        private String unionid;
        private String country;
        private String nickname;
        private String language;
        private String province;
        private String[] privilege;
        private String headimgurl;

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getUnionid() {
            return unionid;
        }

        public void setUnionid(String unionid) {
            this.unionid = unionid;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String[] getPrivilege() {
            return privilege;
        }

        public void setPrivilege(String[] privilege) {
            this.privilege = privilege;
        }

        public String getHeadimgurl() {
            return headimgurl;
        }

        public void setHeadimgurl(String headimgurl) {
            this.headimgurl = headimgurl;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        @Override
        public String toString() {
            return "UserInfo{" +
                    "sex='" + sex + '\'' +
                    ", city='" + city + '\'' +
                    ", openid='" + openid + '\'' +
                    ", unionid='" + unionid + '\'' +
                    ", country='" + country + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", language='" + language + '\'' +
                    ", province='" + province + '\'' +
                    ", privilege=" + Arrays.toString(privilege) +
                    ", headimgurl='" + headimgurl + '\'' +
                    '}';
        }
    }
}
