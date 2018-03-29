package com.iteaj.util.module.authorization.type;

import com.iteaj.util.module.authorization.AuthorizeContext;
import com.iteaj.util.module.authorization.AuthorizePhase;
import com.iteaj.util.module.authorization.AuthorizeResult;
import com.iteaj.util.module.authorization.PhaseChain;
import com.iteaj.util.module.authorization.http.AsyncAuthorizationAbstract;
import com.iteaj.util.module.authorization.http.AuthorizePhaseAbstract;
import com.iteaj.util.module.authorization.http.SessionStorageContext;

import java.io.IOException;

/**
 * <p>淘宝开发平台api 授权认证</p>
 * Create Date By 2017-03-07
 * @author iteaj
 * @since 1.7
 */
public class TaoBaoOpenApiAuthorize extends AsyncAuthorizationAbstract {

    @Override
    protected AuthorizeResult resolve(AuthorizeContext context) {
        return null;
    }

    public enum ViewType{
        Web("对应淘宝浏览器页面样式"),
        Tmall("对应天猫的浏览器页面样式"),
        Wap("对应无线端的浏览器页面样式");
        public String description;
        ViewType(String description){
            this.description = description;
        }
    }

    private TypeEnum typeEnum;
    private String state;
    private ViewType view;
    private String appkey;
    private String AppSecret;
    private String grantType;
    //获取授权code时访问TaoBao api的网关
    private String codeGateway;
//    private String redirectUri;
    private String responseType;
    //获取授权access_token是访问TaoBao api的网关
    private String accessGateway;

    public TaoBaoOpenApiAuthorize(){
        this.view = ViewType.Web;
        this.responseType = "code";
        this.grantType = "authorization_code";
        this.typeEnum = TypeEnum.TaoBaoApi;
    }

    @Override
    public void init() {
        super.init();
        CodePhase codePhase = new CodePhase(null);
        registerAuthorizePhase(new EntryPhase(codePhase));
        registerAuthorizePhase(codePhase);
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
        return "start";
    }

    @Override
    public String getProcessStage() {
        return "start-->code";
    }

    @Override
    public String getAsyncPhase() {
        return "code";
    }

    public ViewType getView() {
        return view;
    }

    public void setView(ViewType view) {
        this.view = view;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    /**
     * 获取授权code时访问TaoBao api的网关
     * @return
     */
    public String getCodeGateway() {
        return codeGateway;
    }

    public void setCodeGateway(String codeGateway) {
        this.codeGateway = codeGateway;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getAppSecret() {
        return AppSecret;
    }

    public void setAppSecret(String appSecret) {
        AppSecret = appSecret;
    }

    /**
     * 获取授权access_token是访问TaoBao api的网关
     * @return
     */
    public String getAccessGateway() {
        return accessGateway;
    }

    public void setAccessGateway(String accessGateway) {
        this.accessGateway = accessGateway;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public class EntryPhase extends AuthorizePhaseAbstract {

        private String html_suf = "';</script></head></html>";
        private String html_pre = "<!DOCTYPE html><html lang=\"zh_cn\"><head><meta charset=\"UTF-8\"><script>location.href='";

        public EntryPhase(AuthorizePhase nextPhase) {
            super(nextPhase);
        }

        @Override
        public String phaseAlias() {
            return "entry";
        }

        @Override
        public void doPhase(PhaseChain chain, SessionStorageContext context) throws IOException {
            StringBuilder sb = new StringBuilder();
            sb.append(codeGateway).append("?response_type=").append(responseType).append("&client_id=").append(appkey)
                    .append("&redirect_uri=").append(getRedirectUrl(context)).append("&state=").append(state).append("&view=").append(view);
            context.getResponse().setContentType("text/html; charset=utf-8");
            context.getResponse().getWriter().print(html_pre+sb.toString()+html_suf);
        }

        @Override
        public String getTypeAlias() {
            return TaoBaoOpenApiAuthorize.this.getTypeAlias();
        }
    }

    /**
     * 淘宝开放平台Api回调返回code码阶段
     */
    public class CodePhase extends AuthorizePhaseAbstract {

        public CodePhase(AuthorizePhase nextPhase) {
            super(nextPhase);
        }

        @Override
        public String phaseAlias() {
            return "code";
        }

        @Override
        public void doPhase(PhaseChain chain, SessionStorageContext context) throws Exception {
            chain.doPhase(context);
        }

        @Override
        public String getTypeAlias() {
            return TaoBaoOpenApiAuthorize.this.getTypeAlias();
        }

    }
}
