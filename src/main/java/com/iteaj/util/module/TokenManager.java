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
    Object getToken(T param);

    /**
     * 强制刷新 并返回刷新后的token
     * @param param
     * @return
     */
    Object refresh(T param);

    /**
     * token循环周期, 一个比率值
     * @return
     */
    double cycleRate();
}
