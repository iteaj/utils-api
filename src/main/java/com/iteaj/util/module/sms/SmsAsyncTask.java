package com.iteaj.util.module.sms;

/**
 * Create Date By 2016/9/26
 *  <p>短信</p>异步任务,此任务交由异步执行器执行{@link SmsDefaultAsyncExecutor}
 * @author iteaj
 * @since 1.7
 */
public class SmsAsyncTask implements Runnable {

    private Sms delegation;
    private SmsEntity smsEntity;
    private SmsAsyncCall asyncCall;

    public SmsAsyncTask(Sms delegation, SmsEntity entity){
        this(delegation, entity, null);
    }

    public SmsAsyncTask(Sms delegation, SmsEntity entity, SmsAsyncCall asyncCall){
        this.delegation = delegation;
        this.smsEntity = entity;
        this.asyncCall = asyncCall;
    }

    @Override
    public void run() {
        if(null == asyncCall) {
            delegation.send(smsEntity);
            return;
        }

        asyncCall.callBack(delegation.send(smsEntity), smsEntity);
    }
}
