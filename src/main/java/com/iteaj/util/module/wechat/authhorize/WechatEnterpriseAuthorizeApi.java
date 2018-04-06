package com.iteaj.util.module.wechat.authhorize;

import com.iteaj.util.CommonUtils;
import com.iteaj.util.HttpUtils;
import com.iteaj.util.JsonUtils;
import com.iteaj.util.module.http.build.EntityBuilder;
import com.iteaj.util.module.http.build.UrlBuilder;
import com.iteaj.util.module.json.JsonWrapper;
import com.iteaj.util.module.oauth2.AbstractAuthorizeResult;
import com.iteaj.util.module.oauth2.AbstractStorageContext;
import com.iteaj.util.module.oauth2.AuthorizePhase;
import com.iteaj.util.module.oauth2.PhaseChain;
import com.iteaj.util.module.wechat.AbstractWechatPhase;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Create Date By 2017-04-27
 *  <p>微信企业号授权</p>
 * @author iteaj
 * @since 1.7
 */
public class WechatEnterpriseAuthorizeApi extends AbstractWechatOAuth2Api
        <WechatEnterpriseAuthorizeConfig, WechatEnterpriseAuthorizeParam> {

    public WechatEnterpriseAuthorizeApi(WechatEnterpriseAuthorizeConfig config) {
        super(config);
    }

    @Override
    public WechatEnterpriseAuthorizeApi build() {
        super.build();
        if(!CommonUtils.isNotBlank(getApiConfig().getAppId())) {
            throw new IllegalArgumentException("corpId参数必须设置");
        }

        if(!CommonUtils.isNotBlank(getApiConfig().getAppSecret())) {
            throw new IllegalArgumentException("corpSecret参数必须设置");
        }

        //注册授权阶段
        UserDetailPhase detailPhase = new UserDetailPhase(null);
        UserPhase userPhase = new UserPhase(detailPhase);
        TokenPhase tokenPhase = new TokenPhase(userPhase);
        CodePhase codePhase = new CodePhase(tokenPhase);

        registerAuthorizePhase(new EntryPhase(codePhase));
        registerAuthorizePhase(codePhase);
        registerAuthorizePhase(tokenPhase);
        registerAuthorizePhase(userPhase);
        registerAuthorizePhase(detailPhase);

        return this;
    }

    @Override
    public Class<? extends AbstractAuthorizeResult> authorizeResult() {
        return WechatEnterpriseResult.class;
    }

    @Override
    public String getDescription() {
        return "微信企业号网页授权";
    }

    @Override
    public String getPhaseEntry() {
        return "entry";
    }

    @Override
    public String getProcessStage() {
        return "entry->code->token->user->detail";
    }

    /**
     * 微信access_token入口阶段
     */
    protected class EntryPhase extends AbstractWechatPhase {

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
        public void doPhase(PhaseChain chain, AbstractStorageContext context) {
            PrintWriter writer = null;
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(getApiConfig().getCodeGateway())
                        .append("?appid=").append(getApiConfig().getAppId())
                        .append("&redirect_uri=").append(getRedirectUrl(context, getApiConfig().getRedirectUrl()))
                        .append("&response_type=").append(getApiConfig().getResponseType())
                        .append("&scope=").append(getApiConfig().getScope())
                        .append("&agentid=").append(getApiConfig().getAgentid())
                        .append("&state=").append(getApiConfig().getState())
                        .append("#wechat_redirect");

                String html = html_pre + sb.toString() + html_suf;
                if(logger.isDebugEnabled()) {
                    logger.debug("响应Html：{}", html);
                }
                writer = context.getResponse().getWriter();
                context.getResponse().setContentType("text/html; charset=utf-8");
                writer.print(html);
            } catch (IOException e) {
                logger.error("类别：微信网页授权 - 动作：获取Code - 描述：获取失败", e);
            } finally {
                writer.flush();
                writer.close();
            }
        }

        @Override
        public String getTypeAlias() {
            return WechatEnterpriseAuthorizeApi.this.getTypeAlias();
        }
    }

    protected class CodePhase extends AbstractWechatPhase {

        public CodePhase(AuthorizePhase nextPhase) {
            super(nextPhase);
        }

        @Override
        public String phaseAlias() {
            return "code";
        }

        @Override
        public void doPhase(PhaseChain chain, AbstractStorageContext context) {
            //获取微信授权code
            String code = context.getRequest().getParameter("code");

            if(!isSuccess(context, code, phaseAlias(), "获取Code失败")) return;

            //将返回的code存储到授权上下文
            context.addContextParam(phaseAlias(), code).addContextParam("state"
                    , context.getContextParam("state"));

            //执行下一阶段：token
            chain.doPhase(context);
        }

        @Override
        public String getTypeAlias() {
            return WechatEnterpriseAuthorizeApi.this.getTypeAlias();
        }
    }

    protected class TokenPhase extends AbstractWechatPhase {

        public TokenPhase(AuthorizePhase nextPhase) {
            super(nextPhase);
        }

        @Override
        public String phaseAlias() {
            return "token";
        }

        @Override
        public void doPhase(PhaseChain chain, AbstractStorageContext context) {
            StringBuilder sb = new StringBuilder(getApiConfig().getAccessGateway()).append("?")
                    .append("corpid=").append(getApiConfig().getAppId())
                    .append("&corpsecret=").append(getApiConfig().getAppSecret());

            String result = HttpUtils.doGet(UrlBuilder.build(sb.toString()), "UTF-8");

            if(!isSuccess(context, result, phaseAlias(), "获取Access_Token失败")) return;

            //存储AccessToken信息到上下文
            JsonWrapper json = JsonUtils.buildJson(result);
            context.addContextParam(phaseAlias(), json);

            //执行下一阶段
            chain.doPhase(context);
        }

        @Override
        public String getTypeAlias() {
            return WechatEnterpriseAuthorizeApi.this.getTypeAlias();
        }
    }

    protected class UserPhase extends AbstractWechatPhase {

        public UserPhase(AuthorizePhase nextPhase) {
            super(nextPhase);
        }

        @Override
        public String phaseAlias() {
            return "user";
        }

        @Override
        public String getTypeAlias() {
            return WechatEnterpriseAuthorizeApi.this.getTypeAlias();
        }

        @Override
        protected void doPhase(PhaseChain chain, AbstractStorageContext context) {
            JsonWrapper token = (JsonWrapper) context.getContextParam("token");
            StringBuilder sb = new StringBuilder(getApiConfig().getUserInfoGateway()).append("?")
                    .append("access_token=").append(token.getNode("access_token").getString())
                    .append("&code=").append(context.getContextParam("code"));
            String result = HttpUtils.doGet(UrlBuilder.build(sb.toString()), "UTF-8");

            //获取用户信息失败
            if(!isSuccess(context, result, phaseAlias(), "获取用户信息失败")) return;

            //存储用户信息到上下文
            WechatEnterpriseResult.UserInfo userInfo = JsonUtils
                    .toBean(result, WechatEnterpriseResult.UserInfo.class);
            context.addContextParam(this.phaseAlias(), userInfo);

            //执行下一阶段
            chain.doPhase(context);
        }
    }

    protected class UserDetailPhase extends AbstractWechatPhase {

        public UserDetailPhase(AuthorizePhase nextPhase) {
            super(nextPhase);
        }

        @Override
        public String phaseAlias() {
            return "detail";
        }

        @Override
        public String getTypeAlias() {
            return WechatEnterpriseAuthorizeApi.this.getTypeAlias();
        }

        @Override
        protected void doPhase(PhaseChain chain, AbstractStorageContext context) {
            JsonWrapper token = (JsonWrapper) context.getContextParam("token");
            WechatEnterpriseResult.UserInfo userInfo =
                    (WechatEnterpriseResult.UserInfo)context.getContextParam("user");

            String accessToken = token.getNode("access_token").getString();
            EntityBuilder builder = EntityBuilder.build(getApiConfig().getUserDetailGateway())
                    .addParam("access_token", accessToken)
                    .addBody(null, "{\"user_ticket\":\""+userInfo.getUser_ticket()+"\"}");

            String result = HttpUtils.doPost(builder, "UTF-8");

            //获取用户详情失败
            if(!isSuccess(context, result, phaseAlias(), "获取用户信息详情失败")) return;

            //存储用户信息到上下文
            WechatEnterpriseResult.UserDetail userDetail = JsonUtils
                    .toBean(result, WechatEnterpriseResult.UserDetail.class);
            context.addContextParam(this.phaseAlias(), userDetail);
        }
    }
}
