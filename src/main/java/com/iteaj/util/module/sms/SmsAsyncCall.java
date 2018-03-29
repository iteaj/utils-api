package com.iteaj.util.module.sms;

/**
 * Create Date By 2016/9/7
 *  异步调用回调接口
 * @author iteaj
 * @since 1.7
 */
public interface SmsAsyncCall {

    /**
     * 回调函数
     * @param status   当前短信发送状态
     * @param entity    当前发送的实体
     */
    void callBack(SendStatus status, SmsEntity entity);
}
