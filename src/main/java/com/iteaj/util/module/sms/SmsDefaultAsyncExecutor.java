package com.iteaj.util.module.sms;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Create Date By 2016/9/27
 *  默认异步执行器
 * @author iteaj
 * @since 1.7
 */
public class SmsDefaultAsyncExecutor implements SmsAsyncExecutor {

    /**默认线程池数量3*/
    public static final int DEFAULT_THREAD_NUM = 3;

    /**线程池数量*/
    private int threadNum;
    /**执行者服务*/
    private ExecutorService executorService;

    public SmsDefaultAsyncExecutor(){
        this(DEFAULT_THREAD_NUM);
    }

    public SmsDefaultAsyncExecutor(int threadNum){
        executorService = Executors.newFixedThreadPool(threadNum);
    }

    @Override
    public void execute(Runnable task) {
        if(null != executorService)
            executorService.execute(task);
    }

    @Override
    public void release() throws InterruptedException {
        if(!executorService.isTerminated())
            executorService.shutdown();

        executorService = null;
    }

    @Override
    public boolean isTerminated() {
        return executorService.isTerminated();
    }

    public int getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }
}
