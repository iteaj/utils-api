package com.iteaj.util;

/**
 * Create Date By 2018-03-13
 *  数据响应, 用来制定服务端响应给客户端的数据规范
 * @author iteaj
 * @since 1.7
 */
public interface ClientResponse<T> {

    /**
     *  返回操作结果, 不过操作成功或者失败, 需要额外响应的数据都应放在此
     * @return
     */
    T getData();

    /**
     * 从Results取Key的数据并返回
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 增加一条与Key为data的Val
     * @param val
     * @return
     */
    ClientResponse<T> add(Object val);

    /**
     * 增加一条与Key对应的Val
     * @param key
     * @param val
     * @return
     */
    ClientResponse<T> add(String key, Object val);

    /**
     * 移除一条与Key对应的值
     * @param key
     * @return
     */
    ClientResponse<T> remove(String key);

    /**
     * 是否包Key的值
     * @param key
     * @return
     */
    boolean contain(String key);

    /**
     * 返回操作的成功或失败的状态
     * @return
     */
    boolean getStatus();

    /**
     * 设置状态   并返回响应对象
     * @param success
     * @return
     */
    ClientResponse<T> setStatus(boolean success);

    /**
     * 返回成功或失败时指定的消息
     * @return
     */
    String getMessage();

    /**
     * 设置消息, 并返回响应对象
     * @param message
     * @return
     */
    ClientResponse<T> setMessage(String message);
}
