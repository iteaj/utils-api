package com.iteaj.util.module.oauth2;

import com.iteaj.util.Const;
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
public abstract class AbstractAuthorizePhase<T extends AbstractStorageContext>
        implements AuthorizePhase<T> {

    private AuthorizePhase nextPhase;
    protected Logger logger = LoggerFactory.getLogger(getClass());
    public static String ERROR_INFO = "授权类型：[{}],在执行阶段：[{}]时失败,失败信息：[{}]";

    public AbstractAuthorizePhase(AuthorizePhase nextPhase) {
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
    public void phase(PhaseChain chain, T context) {
        doPhase(chain, context);
    }

    protected abstract void doPhase(PhaseChain chain, T context);

    protected String getRedirectUrl(AbstractStorageContext context, String redirectUrl){
        try {
            StringBuilder requestURL = new StringBuilder().append(redirectUrl);
            if(-1 != requestURL.indexOf("?"))
                requestURL.append("&");
            else
                requestURL.append('?');

            requestURL.append(Const.CONTEXT_PARAM_KEY)
                    .append('=').append(context.getContextKey());
            return URLEncoder.encode(requestURL.toString(), "utf-8");
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
