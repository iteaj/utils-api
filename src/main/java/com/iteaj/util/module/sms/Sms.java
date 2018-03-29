package com.iteaj.util.module.sms;

/**
 * Create Date By 2016/9/7
 *  短信抽象接口
 * @author iteaj
 * @since 1.7
 */
public interface Sms<T extends SendStatus> {

    T send(SmsEntity entity);

    T send(String content, String... phones);

    SmsConfigure getConfigure();
}
