package com.iteaj.util.module.oauth2;

import java.util.Map;

/**
 * <P>授权类型对象</P>
 * Create Date By 2017-03-06
 * @author iteaj
 * @since 1.7
 */
public interface AuthorizationType {

    /**
     * <p>返回授权类型别名</p>
     * @return
     */
    String getTypeAlias();

    /**
     * 获取认证类型描述
     * @return
     */
    String getDescription();

    /**
     * <p>返回阶段集合的入口阶段</p><br>
     *     一个授权类型有多个授权阶段,所以必须指明哪一个阶段是执行入口
     * @return
     */
    String getPhaseEntry();

    /**
     * 授权类型的阶段流程比如：start-->code-->token
     * @return
     */
    String getProcessStage();

    /**
     * 返回阶段集合
     * @return
     */
    Map<String, AuthorizePhase> getPhases();

    /**
     * 返回一个授权阶段
     * @param phase
     * @return
     */
    AuthorizePhase getAuthorizePhase(String phase);

    /**
     * 注册授权阶段
     * @param phase
     */
    void registerAuthorizePhase(AuthorizePhase phase);


    /**
     * 返回此授权类型的授权结果类型
     * @return
     */
    Class<? extends AbstractAuthorizeResult> authorizeResult();

}
