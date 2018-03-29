package com.iteaj.util.module.authorization;

/**
 * <p>授权认证阶段执行链</p>
 * Create Date By 2017-03-08
 * @author iteaj
 * @since 1.7
 */
public interface PhaseChain {

    void doPhase(AuthorizeContext context) throws Exception;
}
