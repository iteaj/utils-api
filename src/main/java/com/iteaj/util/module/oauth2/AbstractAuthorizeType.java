package com.iteaj.util.module.oauth2;

import com.iteaj.util.CommonUtils;
import com.iteaj.util.UtilsException;
import com.iteaj.util.UtilsType;
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
public abstract class AbstractAuthorizeType<C extends AbstractStorageContext>
        implements AuthorizationType {

    private Map<String, AuthorizePhase> phases;
    protected Logger logger = LoggerFactory.getLogger(getClass());

    public AbstractAuthorizeType() {
        this.phases = new HashMap<>();
    }

    public AbstractAuthorizeType build() {
        if(logger.isDebugEnabled())
            logger.debug("初始化授权类型:[{}] 类型描述：[{}] 阶段流程：[{}]",getClass().getSimpleName()
                    , getDescription(), getProcessStage());

        return this;
    }

    /**
     * 执行动作
     * @param param
     * @return
     */
    public CallAction invoke(C param) throws UtilsException {
        try {
            //初始化上下文
            param.initContext(this);

            //开始执行阶段
            HashPhaseChain.instance().doPhase(param);

            return new CallAction(param);
        } catch (UtilsException e) {
            throw e;
        }catch (Exception e) {
            throw new UtilsException("OAuth2 - 执行错误：", e, UtilsType.OAuth2);
        }
    }

    @Override
    public String getTypeAlias() {
        return getClass().getName();
    }

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
        if(phase == null || !CommonUtils.isNotBlank(phase.phaseAlias()))
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

    public class CallAction {

        private AbstractStorageContext context;

        public CallAction(AbstractStorageContext context) {
            this.context = context;
        }

        public void call(AbstractAuthorizeAction action) {
            context.setAuthorizeAction(action);
        }
    }
}
