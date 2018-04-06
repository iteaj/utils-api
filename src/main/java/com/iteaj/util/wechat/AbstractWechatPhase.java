package com.iteaj.util.wechat;

import com.iteaj.util.CommonUtils;
import com.iteaj.util.module.oauth2.AbstractAuthorizePhase;
import com.iteaj.util.module.oauth2.AbstractStorageContext;
import com.iteaj.util.module.oauth2.AuthorizePhase;

/**
 * create time: 2018/4/6
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public abstract class AbstractWechatPhase extends AbstractAuthorizePhase {

    public AbstractWechatPhase(AuthorizePhase nextPhase) {
        super(nextPhase);
    }

    protected boolean isSuccess(AbstractStorageContext context, String result, String alias, String msg) {
        if(CommonUtils.isBlank(result)){
            logger.error(ERROR_INFO, getTypeAlias(), alias, msg);

            context.getAuthorizeResult().setSuccess(false).setErrMsg(msg);
            return false;
        } else if(result.contains("errcode")) {
            logger.error(ERROR_INFO, getTypeAlias(), alias, result);

            context.getAuthorizeResult().setSuccess(false).setErrMsg(result);
            return false;
        }

        return true;
    }
}
