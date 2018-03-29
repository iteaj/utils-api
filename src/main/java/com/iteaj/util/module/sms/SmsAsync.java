package com.iteaj.util.module.sms;

/**
 * Create Date By 2016/9/27
 *  短信异步接口
 * @author iteaj
 * @since 1.7
 */
public interface SmsAsync extends Sms {

    void send(Runnable task);

    void send(SmsEntity entity, SmsAsyncCall callBack);
}
