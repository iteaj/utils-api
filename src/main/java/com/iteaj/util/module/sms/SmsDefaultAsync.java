package com.iteaj.util.module.sms;

import com.iteaj.util.module.sms.smsBao.SmsBao;

/**
 * Create Date By 2016/9/27
 *  异步接口
 *  通过{@link SmsAsyncExecutor}异步执行器进行异步发送短信,
 *      真正发送的代码委托给接口{@link Sms}的实现者eg: {@link SmsBao}.
 *
 * @author iteaj
 * @since 1.7
 */
public class SmsDefaultAsync implements SmsAsync {

    private static Sms delegation;
    private SmsAsyncExecutor executor;
    private static SmsDefaultAsync smsDefaultAsync;

    public synchronized static SmsDefaultAsync instance(Sms delegation){
        return instance(delegation, new SmsDefaultAsyncExecutor());
    }

    public synchronized static SmsDefaultAsync instance(Sms delegation, SmsDefaultAsyncExecutor executor){
        if(smsDefaultAsync == null)
            smsDefaultAsync = new SmsDefaultAsync(delegation, executor);

        return smsDefaultAsync;
    }

    private SmsDefaultAsync(Sms delegation, SmsAsyncExecutor executor) {
        this.executor = executor;
        SmsDefaultAsync.delegation = delegation;
    }

    @Override
    public void send(Runnable task) {
        executor.execute(task);
    }

    @Override
    public void send(SmsEntity entity, SmsAsyncCall callBack) {
        executor.execute(new SmsAsyncTask(delegation,entity, callBack));
    }

    @Override
    public SendStatus send(SmsEntity entity) {
        executor.execute(new SmsAsyncTask(delegation, entity));
        return null;
    }

    @Override
    public SendStatus send(String content, String... phones) {
        return send(new SmsEntity(content, phones));
    }

    @Override
    public SmsConfigure getConfigure() {
        return delegation.getConfigure();
    }
}
