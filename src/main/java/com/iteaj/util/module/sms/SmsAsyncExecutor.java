package com.iteaj.util.module.sms;

/**
 * Create Date By 2016/9/27
 *  短信发送异步执行者
 * @author iteaj
 * @since 1.7
 */
public interface SmsAsyncExecutor {

    /**
     * 执行一个任务
     * @param task
     */
    void execute(Runnable task);

    /**
     * 释放线程池
     */
    void release() throws InterruptedException;

    /**
     * ExecutorService是否终止
     * @return
     */
    boolean isTerminated();
}
