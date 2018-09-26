package com.iteaj.util.module.wechat.authhorize;

import com.iteaj.util.AssertUtils;
import com.iteaj.util.CommonUtils;
import com.iteaj.util.HttpUtils;
import com.iteaj.util.JsonUtils;
import com.iteaj.util.core.UtilsType;
import com.iteaj.util.module.http.build.MultipartBuilder;
import com.iteaj.util.module.http.build.UrlBuilder;
import com.iteaj.util.module.json.Json;
import com.iteaj.util.module.oauth2.AbstractAuthorizeResult;
import com.iteaj.util.module.oauth2.AuthorizePhase;
import com.iteaj.util.module.oauth2.PhaseChain;
import com.iteaj.util.module.wechat.AbstractWechatPhase;
import com.iteaj.util.module.wechat.WechatApiType;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Create Date By 2017-04-27
 *  <p>微信企业号授权</p>
 * @author iteaj
 * @since 1.7
 */
public class WxaEnterpriseAuthorize extends AbstractWechatOAuth2Api
        <WxcEnterpriseAuthorize, WxpEnterpriseAuthorize> {

    public WxaEnterpriseAuthorize(WxcEnterpriseAuthorize config) {
        super(config);
    }

    @Override
    public WxaEnterpriseAuthorize build() {
        super.build();
        AssertUtils.isNotBlank(getApiConfig().getAppId()
                , "企业授权接口corpId参数必须设置", UtilsType.WECHAT);

        AssertUtils.isNotBlank(getApiConfig().getAppSecret()
                , "corpSecret参数必须设置", UtilsType.WECHAT);

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

    @Override
    public WechatApiType getApiType() {
        return WechatApiType.EnterpriseAuthorize;
    }

    /**
     * 微信access_token入口阶段
     */
    protected class EntryPhase extends AbstractWechatPhase<WxpEnterpriseAuthorize> {

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
        public void doPhase(PhaseChain chain, WxpEnterpriseAuthorize context) {
            PrintWriter writer = null;
            try {
                StringBuilder sb = new StringBuilder(html_pre);
                //授权参数的redirectUrl覆盖授权配置里面的redirectUrl
                String redirectUrl = CommonUtils.isBlank(context.getRedirectUrl())
                        ?getApiConfig().getRedirectUrl():context.getRedirectUrl();
                AssertUtils.isNotBlank(redirectUrl, "请指定企业微信网页授权的RedirectUrl参数", UtilsType.WECHAT);

                sb.append(getApiConfig().getCodeGateway())
                        .append("?appid=").append(getApiConfig().getAppId())
                        .append("&redirect_uri=").append(getRedirectUrl(context, redirectUrl))
                        .append("&response_type=").append(getApiConfig().getResponseType())
                        .append("&scope=").append(context.getScope().val)
                        .append("&agentid=").append(getApiConfig().getAgentid())
                        .append("&state=").append(getApiConfig().getState())
                        .append("#wechat_redirect");
                sb.append(html_suf);

                String html = sb.toString();
                if(logger.isDebugEnabled()) {
                    logger.debug("响应Html：{}", html);
                }
                writer = context.getResponse().getWriter();
                context.getResponse().setContentType("text/html; charset=utf-8");
                writer.print(html);
            } catch (IOException e) {
                logger.error("类别：微信Api - 动作：获取Code - 描述：获取失败", e);
            } finally {
                writer.flush();
                writer.close();
            }
        }

        @Override
        public String getTypeAlias() {
            return WxaEnterpriseAuthorize.this.getTypeAlias();
        }
    }

    protected class CodePhase extends AbstractWechatPhase<WxpEnterpriseAuthorize> {

        public CodePhase(AuthorizePhase nextPhase) {
            super(nextPhase);
        }

        @Override
        public String phaseAlias() {
            return "code";
        }

        @Override
        public void doPhase(PhaseChain chain, WxpEnterpriseAuthorize context) {
            //获取微信授权code
            String code = context.getRequest().getParameter("code");

            if(!isSuccess(context, code, phaseAlias())) return;

            //将返回的code存储到授权上下文
            context.addContextParam(phaseAlias(), code).addContextParam("state"
                    , context.getContextParam("state"));

            //执行下一阶段：token
            chain.doPhase(context);
        }

        @Override
        public String getTypeAlias() {
            return WxaEnterpriseAuthorize.this.getTypeAlias();
        }
    }

    protected class TokenPhase extends AbstractWechatPhase<WxpEnterpriseAuthorize> {

        public TokenPhase(AuthorizePhase nextPhase) {
            super(nextPhase);
        }

        @Override
        public String phaseAlias() {
            return "token";
        }

        @Override
        public void doPhase(PhaseChain chain, WxpEnterpriseAuthorize context) {
            StringBuilder sb = new StringBuilder(getApiConfig().getAccessGateway()).append("?")
                    .append("corpid=").append(getApiConfig().getAppId())
                    .append("&corpsecret=").append(getApiConfig().getAppSecret());

            String result = HttpUtils.doGet(UrlBuilder.build(sb.toString()), "UTF-8");

            if(!isSuccess(context, result, phaseAlias())) return;

            //存储AccessToken信息到上下文
            Json json = JsonUtils.builder(result);
            context.addContextParam(phaseAlias(), json);

            //执行下一阶段
            chain.doPhase(context);
        }

        @Override
        public String getTypeAlias() {
            return WxaEnterpriseAuthorize.this.getTypeAlias();
        }
    }

    protected class UserPhase extends AbstractWechatPhase<WxpEnterpriseAuthorize> {

        public UserPhase(AuthorizePhase nextPhase) {
            super(nextPhase);
        }

        @Override
        public String phaseAlias() {
            return "user";
        }

        @Override
        public String getTypeAlias() {
            return WxaEnterpriseAuthorize.this.getTypeAlias();
        }

        @Override
        protected void doPhase(PhaseChain chain, WxpEnterpriseAuthorize context) {
            Json token = (Json) context.getContextParam("token");
            StringBuilder sb = new StringBuilder(getApiConfig().getUserInfoGateway()).append("?")
                    .append("access_token=").append(token.getString("access_token"))
                    .append("&code=").append(context.getContextParam("code"));
            String result = HttpUtils.doGet(UrlBuilder.build(sb.toString()), "UTF-8");

            //获取用户信息失败
            if(!isSuccess(context, result, phaseAlias())) return;

            //存储用户信息到上下文
            WechatEnterpriseResult.UserInfo userInfo = JsonUtils
                    .toBean(result, WechatEnterpriseResult.UserInfo.class);
            if(!userInfo.success()){
                logger.error(ERROR_INFO, getTypeAlias(), phaseAlias(), result);
                context.getAuthorizeResult().setSuccess(false).setErrMsg(result);
                return;
            }

            context.addContextParam(this.phaseAlias(), userInfo);

            //执行下一阶段
            chain.doPhase(context);
        }
    }

    protected class UserDetailPhase extends AbstractWechatPhase<WxpEnterpriseAuthorize> {

        public UserDetailPhase(AuthorizePhase nextPhase) {
            super(nextPhase);
        }

        @Override
        public String phaseAlias() {
            return "detail";
        }

        @Override
        public String getTypeAlias() {
            return WxaEnterpriseAuthorize.this.getTypeAlias();
        }

        @Override
        protected void doPhase(PhaseChain chain, WxpEnterpriseAuthorize context) {
            Json token = (Json) context.getContextParam("token");
            WechatEnterpriseResult.UserInfo userInfo =
                    (WechatEnterpriseResult.UserInfo)context.getContextParam("user");

            String accessToken = token.getString("access_token");
            MultipartBuilder builder = MultipartBuilder.build(getApiConfig().getUserDetailGateway())
                    .addParam("access_token", accessToken)
                    .addBody(null, "{\"user_ticket\":\""+userInfo.getUser_ticket()+"\"}");

            String result = HttpUtils.doPost(builder, "UTF-8");

            //获取用户详情失败
            if(!isSuccess(context, result, phaseAlias())) return;

            //存储用户信息到上下文
            WechatEnterpriseResult.UserDetail userDetail = JsonUtils
                    .toBean(result, WechatEnterpriseResult.UserDetail.class);
            if(!userDetail.success()){
                logger.error(ERROR_INFO, getTypeAlias(), phaseAlias(), "获取用户信息详情失败");
                context.getAuthorizeResult().setSuccess(false).setErrMsg("获取用户信息详情失败");
                return;
            }

            context.addContextParam(this.phaseAlias(), userDetail);
        }
    }
}
