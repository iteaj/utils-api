package com.iteaj.util.module.authorization.http;

import com.iteaj.util.module.authorization.*;
import com.iteaj.util.http.HttpAdapter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>以http形式的授权类型</p>
 * Create Date By 2017-03-07
 * @author iteaj
 * @since 1.7
 */
public abstract class AuthorizationTypeAbstract
        implements AuthorizationType {
    private static String authorizeServletUrl;
    private Map<String, AuthorizePhase> phases;
    protected static PhaseChain phaseChain = new HashPhaseChain();
    protected Logger logger = LoggerFactory.getLogger(getClass());

    public AuthorizationTypeAbstract() {
        this.phases = new HashMap<>();
    }

    @Override
    public void init() {
        if(StringUtils.isBlank(getAuthorizeServletUrl()))
            throw new IllegalArgumentException("请配置Servlet："+
                    AuthorizeServlet.class.getName()+" 并设置其URL：authorizeServletUrl");

        if(logger.isDebugEnabled())
            logger.info("初始化授权类型:[{}] 类型描述：[{}] 阶段流程：[{}]",getClass().getSimpleName()
                    , getDescription(), getProcessStage());
    }

    protected abstract AuthorizeResult resolve(AuthorizeContext context);

    @Override
    public Map<String, AuthorizePhase> getPhases() {
        return phases;
    }

    @Override
    public AuthorizePhase getAuthorizePhase(String phase) {
        return phases.get(phase);
    }

    @Override
    public void registerAuthorizePhase(AuthorizePhase phase) {
        if(phase == null || StringUtils.isBlank(phase.phaseAlias()))
            throw new IllegalArgumentException("阶段不能为空或者阶段类型不能为空");

        if(phases.containsKey(phase.phaseAlias())){
            logger.warn("类型：[{}] 里面的 [{}] 阶段将被覆写", getClass().getSimpleName()
                    , phase.getClass().getSimpleName());
            phases.put(phase.phaseAlias(), phase);
            return;
        }

        logger.info("注册阶段：[{}] 到类型：[{}] 阶段别名：[{}]", phase.getClass().getSimpleName(),
                getClass().getSimpleName(), phase.phaseAlias());

        phases.put(phase.phaseAlias(), phase);
    }

    @Override
    public String getAuthorizeServletUrl() {
        return authorizeServletUrl;
    }

    /**
     * @see AuthorizeServlet 此servlet对象的url
     * 注：已经包含上下文路径,请不要重复指定
     * @param authorizeServletUrl
     */
    public void setAuthorizeServletUrl(String authorizeServletUrl) {
        AuthorizationTypeAbstract.authorizeServletUrl = authorizeServletUrl;
    }


}
