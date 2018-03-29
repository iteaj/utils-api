package com.iteaj.util.module.authorization;

import com.iteaj.util.module.authorization.http.AuthorizationTypeAbstract;

/**
 * Created by iteaj on 2017/3/15.
 */
public abstract class SyncAuthorizeType<T extends SyncResult>
        extends AuthorizationTypeAbstract {

    public final T invoke(AuthorizeContext context){
        try {
            SyncAuthorizeAction authorizeAction = new SyncAuthorizeAction();

            //初始化上下文
            context.init(this, authorizeAction);

            //执行授权阶段
            phaseChain.doPhase(context);

            //返回结果
            return (T)authorizeAction.getResult();
        } catch (Exception e) {
            logger.error("执行授权：[{}]时异常：", getTypeAlias(), e);
            return null;
        }
    }
}
