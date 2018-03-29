package com.iteaj.util.module.authorization.http;

import com.iteaj.util.module.authorization.AuthorizeContext;
import com.iteaj.util.module.authorization.AuthorizePhase;
import com.iteaj.util.module.authorization.PhaseChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create Date By 2017-03-08
 * @author iteaj
 * @since 1.7
 */
public class HashPhaseChain implements PhaseChain {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void doPhase(AuthorizeContext context) throws Exception {
        //获取要被执行的阶段
        AuthorizePhase execPhase = context.getTickPhase();

        //如果执行阶段为空则返回
        if(null == execPhase) return;

        AuthorizationTypeAbstract typeAbstract = (AuthorizationTypeAbstract) context.getType();
        if(logger.isDebugEnabled())
            logger.debug("授权类型：[{}] 的[{}]阶段正被执行...",
                    context.getType().getTypeAlias(), execPhase.phaseAlias());

        //执行阶段
        execPhase.phase(this, context);

        //当前执行的阶段刚好等于授权动作要调用的阶段
        if(execPhase.phaseAlias().equals(context.getAuthorizeAction().getInvokePhase())) {
            try {
                //调用动作
                context.getAuthorizeAction().call(typeAbstract.resolve(context));
            } finally {
                //释放上下文
                context.release();
            }
        }
    }
}
