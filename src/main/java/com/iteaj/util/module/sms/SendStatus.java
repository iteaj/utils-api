package com.iteaj.util.module.sms;

/**
 * Create Date By 2016/9/27
 *  短信的发送状态
 * @author iteaj
 * @since 1.7
 */
public interface SendStatus {

    /**
     * 是否发送成功
     * @return
     */
    boolean isSuccess();

    /**
     * 返回的状态码
     * @return
     */
    Integer getStatus();

    /**
     * 返回的消息
     * @return
     */
    String getErrMsg();
}
