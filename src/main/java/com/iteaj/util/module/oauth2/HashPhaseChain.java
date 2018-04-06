package com.iteaj.util.module.oauth2;

import com.iteaj.util.UtilsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create Date By 2017-03-08
 * @author iteaj
 * @since 1.7
 */
public class HashPhaseChain implements PhaseChain {

    private static PhaseChain chain = new HashPhaseChain();
    private Logger logger = LoggerFactory.getLogger(getClass());

    protected HashPhaseChain() {}

    protected static PhaseChain instance() {
        return chain;
    }

    @Override
    public void doPhase(AbstractStorageContext context) {
        //获取要被执行的阶段
        AuthorizePhase execPhase = context.getNextPhase();

        //如果执行阶段为空则返回
        if(null == execPhase) return;

        AbstractAuthorizeType type = (AbstractAuthorizeType) context.getType();
        if(logger.isDebugEnabled())
            logger.debug("授权类型：[{}] 的[{}]阶段正被执行...",
                    type.getTypeAlias(), execPhase.phaseAlias());

        //执行阶段
        execPhase.phase(this, context);
    }
}
