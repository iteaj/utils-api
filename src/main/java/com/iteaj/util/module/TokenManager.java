package com.iteaj.util.module;

/**
 * create time: 2018/4/14
 *  Oauth2  token管理
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public interface TokenManager<T> {

    /**
     *  返回一个Token
     * @return
     */
    Object getToken(T config);

    /**
     * 强制刷新 并返回刷新后的token
     * @param config
     * @return
     */
    Object refresh(T config);
}
