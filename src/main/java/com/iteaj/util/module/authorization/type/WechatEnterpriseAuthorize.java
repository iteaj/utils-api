package com.iteaj.util.module.authorization.type;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iteaj.util.module.authorization.*;
import com.iteaj.util.module.authorization.http.AsyncAuthorizationAbstract;
import com.iteaj.util.module.authorization.http.AuthorizePhaseAbstract;
import com.iteaj.util.module.authorization.http.SessionStorageContext;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Create Date By 2017-04-27
 *  <p>微信企业号授权</p>
 * @author iteaj
 * @since 1.7
 */
public class WechatEnterpriseAuthorize extends
        AsyncAuthorizationAbstract<AsyncActionAbstract<WechatEnterpriseResult>> {

    private String state;
    private String scope;
    private String corpId;
    private String userType;
    private String agentid;
    private String corpSecret;
    private TypeEnum typeEnum;
    private String responseType;
    private String codeGateway;
    private String accessGateway;
    private String userInfoGateway;
    private String userDetailGateway;

    public WechatEnterpriseAuthorize() {
        this.state = "auth";
        this.responseType = "code";
        this.typeEnum = TypeEnum.WechatEnterpriseAuthorize;
        this.accessGateway = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
        this.codeGateway = "https://open.weixin.qq.com/connect/oauth2/authorize";
        this.userInfoGateway = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo";
        this.userDetailGateway ="https://qyapi.weixin.qq.com/cgi-bin/user/getuserdetail";
    }

    @Override
    public void init() {
        super.init();
        if(StringUtils.isBlank(corpId))
            throw new IllegalArgumentException("corpId参数必须设置");

        if(StringUtils.isBlank(corpSecret))
            throw new IllegalArgumentException("corpSecret参数必须设置");

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
        return "entry->code->token->user->detail";
    }

    @Override
    public String getAsyncPhase() {
        return "detail";
    }

    @Override
    protected AuthorizeResult resolve(AuthorizeContext context) {
        return new WechatEnterpriseResult((SessionStorageContext) context);
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getCodeGateway() {
        return codeGateway;
    }

    public void setCodeGateway(String codeGateway) {
        this.codeGateway = codeGateway;
    }

    public String getUserInfoGateway() {
        return userInfoGateway;
    }

    public void setUserInfoGateway(String userInfoGateway) {
        this.userInfoGateway = userInfoGateway;
    }

    public String getAccessGateway() {
        return accessGateway;
    }

    public void setAccessGateway(String accessGateway) {
        this.accessGateway = accessGateway;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getCorpSecret() {
        return corpSecret;
    }

    public void setCorpSecret(String corpSecret) {
        this.corpSecret = corpSecret;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getAgentid() {
        return agentid;
    }

    public void setAgentid(String agentid) {
        this.agentid = agentid;
    }

    public String getUserDetailGateway() {
        return userDetailGateway;
    }

    public void setUserDetailGateway(String userDetailGateway) {
        this.userDetailGateway = userDetailGateway;
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
                sb.append(codeGateway).append("?appid=").append(corpId)
                        .append("&redirect_uri=").append(getRedirectUrl(context))
                        .append("&response_type=").append(responseType)
                        .append("&scope=").append(scope)
                        .append("&agentid=").append(agentid)
                        .append("&state=").append(state)
                        .append("#wechat_redirect");

                String html = html_pre + sb.toString() + html_suf;
                if(logger.isDebugEnabled()) {
                    logger.debug("响应Html：{}", html);
                }
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
            return WechatEnterpriseAuthorize.this.getTypeAlias();
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
            context.addParam(phaseAlias(), code).addParam("state"
                    , context.getParam("state"));

            //执行下一阶段：token
            chain.doPhase(context);
        }

        @Override
        public String getTypeAlias() {
            return WechatEnterpriseAuthorize.this.getTypeAlias();
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
                    .append("corpid=").append(corpId).append("&corpsecret=").append(corpSecret);
            String result = http.get(sb.toString());

            if(StringUtils.isBlank(result)) {
                logger.error(ERROR_INFO,getTypeAlias(), phaseAlias(), result);
                return;
            }

            //存储AccessToken信息到上下文
            JSONObject accessToken = JSON.parseObject(result);
            context.addParam(phaseAlias(), accessToken);

            //执行下一阶段
            chain.doPhase(context);
        }

        @Override
        public String getTypeAlias() {
            return WechatEnterpriseAuthorize.this.getTypeAlias();
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
            return WechatEnterpriseAuthorize.this.getTypeAlias();
        }

        @Override
        protected void doPhase(PhaseChain chain, SessionStorageContext context) throws Exception {
            JSONObject token = (JSONObject) context.getParam("token");
            StringBuilder sb = new StringBuilder(userInfoGateway).append("?")
                    .append("access_token=").append(token.get("access_token"))
                    .append("&code=").append(context.getParam("code"));
            String result = http.get(sb.toString());

            //获取用户信息失败
            if(StringUtils.isBlank(result)){
                logger.error(ERROR_INFO, getTypeAlias(), phaseAlias(), result);
                return;
            }
            System.out.print("-----userInfo-----:"+result);
            //存储用户信息到上下文
            WechatEnterpriseResult.UserInfo userInfo = JSON
                    .parseObject(result, WechatEnterpriseResult.UserInfo.class);
            context.addParam(this.phaseAlias(), userInfo);

            //执行下一阶段
            chain.doPhase(context);
        }
    }

    protected class UserDetailPhase extends AuthorizePhaseAbstract {

        public UserDetailPhase(AuthorizePhase nextPhase) {
            super(nextPhase);
        }

        @Override
        public String phaseAlias() {
            return "detail";
        }

        @Override
        public String getTypeAlias() {
            return WechatEnterpriseAuthorize.this.getTypeAlias();
        }

        @Override
        protected void doPhase(PhaseChain chain, SessionStorageContext context) throws Exception {
            JSONObject token = (JSONObject) context.getParam("token");
            WechatEnterpriseResult.UserInfo userInfo =
                    (WechatEnterpriseResult.UserInfo)context.getParam("user");

//            byte[] bytes = httpSupport.doPost(userDetailGateway + "?access_token="+token.get("access_token")
//                    , null, new StringEntity("{\"user_ticket\":\""+userInfo.getUser_ticket()+"\"}"));
            String result = http.get("");

            //获取用户详情失败
            if(StringUtils.isBlank(result)){
                logger.error(ERROR_INFO, getTypeAlias(), phaseAlias(), result);
                return;
            }

            //存储用户信息到上下文
            WechatEnterpriseResult.UserDetail userDetail = JSON
                    .parseObject(result, WechatEnterpriseResult.UserDetail.class);
            context.addParam(this.phaseAlias(), userDetail);
        }
    }
}
