package com.iteaj.util.module.wechat;

import com.iteaj.util.CommonUtils;
import com.iteaj.util.module.oauth2.*;

/**
 * create time: 2018/4/6
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public abstract class AbstractWechatPhase<T extends OAuth2ApiParam> extends AbstractAuthorizePhase<T> {

    public AbstractWechatPhase(AuthorizePhase nextPhase) {
        super(nextPhase);
    }

    protected boolean isSuccess(AbstractStorageContext context, String result, String alias) {
        if(CommonUtils.isBlank(result)){
            logger.error(ERROR_INFO, getTypeAlias(), alias);

            context.getAuthorizeResult().setSuccess(false).setErrMsg(result);
            return false;
        }

        return true;
    }

    @Override
    protected abstract void doPhase(PhaseChain chain, T context);
}
