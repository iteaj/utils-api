package com.iteaj.util.module.oauth2;

/**
 * create time: 2018/4/5
 *  授权存储管理, 用来存放{@link AuthorizeContext}
 * @see AuthorizeContext#getContextKey() 通过此key来获取一个上下文
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public interface AuthorizeStorageManager {

    String STORAGE_MANAGER = AuthorizeStorageManager.class.getName();

    /**
     * 返回一个超时时间 毫秒单位
     * 超过这个时间上下文就会被清除掉
     * @return
     */
    long getTimeout();

    /**
     * 返回一个存储上下文
     * @param key
     * @param <T>
     * @return
     */
    <T extends AuthorizeContext> T getContext(String key);

    /**
     * 新增一个上下文对象
     * @param key
     * @param context
     */
    void putContext(String key, AuthorizeContext context);

    /**
     * 移除指定Key的上下文
     * @param key
     */
    AuthorizeContext removeContext(String key);
}
