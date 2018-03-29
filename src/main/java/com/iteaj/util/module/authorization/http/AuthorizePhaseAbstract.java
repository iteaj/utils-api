package com.iteaj.util.module.authorization.http;

import com.iteaj.util.module.authorization.AuthorizePhase;
import com.iteaj.util.module.authorization.PhaseChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * <p>Http形式的授权阶段</p>
 * Create Date By 2017-03-07
 * @author iteaj
 * @since 1.7
 */
public abstract class AuthorizePhaseAbstract implements AuthorizePhase<SessionStorageContext> {

    private AuthorizePhase nextPhase;
    public static String ERROR_INFO = "授权类型：[{}],在执行阶段：[{}]时失败,失败信息：[{}]";
    protected Logger logger = LoggerFactory.getLogger(getClass());

    public AuthorizePhaseAbstract(AuthorizePhase nextPhase) {
        this.nextPhase = nextPhase;
    }

    @Override
    public AuthorizePhase nextPhase() {
        return nextPhase;
    }

    /**
     * @param chain
     * @param context
     * @throws IOException
     */
    @Override
    public void phase(PhaseChain chain, SessionStorageContext context) throws Exception {
        doPhase(chain, context);
    }

    protected abstract void doPhase(PhaseChain chain, SessionStorageContext context) throws Exception;

    protected String getRedirectUrl(SessionStorageContext context){
        try {
//            HttpServletRequest request = context.getRequest();
            String servletUrl = (context.getType()).getAuthorizeServletUrl();
            StringBuilder requestURL = new StringBuilder()
                    .append(servletUrl).append('?').append("timestamp=").append(System.currentTimeMillis());

            //增加原Url链接里面的参数
//            Map parameterMap = request.getParameterMap();
//            if(parameterMap != null && !parameterMap.isEmpty()) {
//                Iterator iterator = parameterMap.entrySet().iterator();
//                while (iterator.hasNext()) {
//                    Map.Entry<String, String[]> next = (Map.Entry<String, String[]>) iterator.next();
//                    requestURL.append("&").append(next.getKey()).append('=').append(next.getValue()[0]);
//                }
//            }

            return URLEncoder.encode(requestURL.toString(), "utf-8");
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
