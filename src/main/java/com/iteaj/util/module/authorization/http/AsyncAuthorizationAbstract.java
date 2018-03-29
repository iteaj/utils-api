package com.iteaj.util.module.authorization.http;

import com.iteaj.util.module.authorization.AuthorizeActionAbstract;

/**
 * Create Date By 2017-03-07
 * @author iteaj
 * @since 1.7
 */
public abstract class AsyncAuthorizationAbstract<K extends AuthorizeActionAbstract>
        extends AuthorizationTypeAbstract {

    public final void invoke(SessionStorageContext context, K callBack) throws Exception {
        //初始化上下文
        context.init(this, callBack);

        phaseChain.doPhase(context);
    }
}
