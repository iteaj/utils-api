package com.iteaj.util.module.oauth2;

import com.iteaj.util.core.UtilsException;

import java.io.Serializable;

/**
 * <p>认证阶段</p><br>
 *     每个认证阶段代表着认证期间的一个步骤或者动作
 *     @see AuthorizationType#getPhases() 返回一个阶段集合因为每一个认证类型都包含着多个认证阶段
 *     注：在执行阶段的时候必须指定要执行的下一个阶段
 * Create Date By 2017-03-07
 * @author iteaj
 * @since 1.7
 */
public interface AuthorizePhase extends Serializable {

    /**
     * 返回当前阶段标识
     * @see AuthorizationType#getPhases() 必须在阶段类型里面唯一
     * @see AuthorizationType#getAuthorizePhase(String) 返回一个阶段
     * @return
     */
    String phaseAlias();

    /**
     * 返回下一个阶段的标识
     * @return
     */
    AuthorizePhase nextPhase();

    /**
     * 返回认证类型{@link AuthorizationType}所属Key
     *  用来指定此阶段属于哪个认证类型
     * @return
     */
    String getTypeAlias();

    /**
     * 执行此阶段
     * @return
     */
    void phase(PhaseChain chain, AbstractStorageContext context) throws UtilsException;
}
