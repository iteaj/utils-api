package com.iteaj.util.module.authorization;

import java.util.Map;

/**
 * <p>认证授权工厂</p><br>
 *     用来存储授权类型列表{@link AuthorizationType}
 * Create Date By 2017-03-06
 * @author iteaj
 * @since 1.7
 */
public interface TypeFactory {

    /**
     * <p>返回一个授权类型对象</p>
     * @param clazz
     * @param <T>
     * @return
     */
    <T extends AuthorizationType> T getType(Class<T> clazz);

    /**
     * <p>返回一个授权类型对象</p>
     * @param keyAlias
     * @return
     */
    <T extends AuthorizationType> T getType(String keyAlias, Class<T> clazz);

    /**
     * <p>返回授权类型Map对象</p>
     * @return
     */
    Map<String, AuthorizationType> getTypes();

    /**
     * <p>注册授权类型</p>
     * @param type
     */
    void registerAuthorizationType(AuthorizationType type);
}
