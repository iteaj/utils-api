package com.iteaj.util.core.task;

import com.iteaj.util.AssertUtils;
import com.iteaj.util.core.UtilsType;

import java.util.concurrent.TimeUnit;

/**
 * create time: 2018/4/9
 *  超时任务
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public abstract class TimeoutTask implements Runnable, Comparable<TimeoutTask> {

    private long timeout;
    private long createTime;

    public TimeoutTask(long timeout, TimeUnit unit) {
        AssertUtils.isTrue(unit != null &&
                unit != TimeUnit.NANOSECONDS &&
                unit != TimeUnit.MICROSECONDS,
                "请指定超时时间单位且必须在毫秒以上(包含毫秒)", UtilsType.TimeoutTask);
        this.timeout = unit.toMillis(timeout);
        if(timeout < 0) throw new IllegalArgumentException("超时时间必须为大于0的整数");
    }

    public TimeoutTask build() {
        this.createTime = System.currentTimeMillis();
        return this;
    }

    public TimeoutTask build(long startMillis) {
        this.createTime = startMillis;
        return this;
    }

    /**
     * 按剩余时间做升序
     * @param o
     * @return
     */
    @Override
    public int compareTo(TimeoutTask o) {
        long currentTime = System.currentTimeMillis();

        long timeout1 = currentTime-this.createTime; //当前对象离创建时过了多长时间
        long timeout2 = currentTime-o.createTime;    //比对对象离创建时过了多长时间

        //如果过去的时间大于指定的超时时间 说明已经超时
        boolean thisTimeout = timeout1 > this.timeout; //指明当前对象是否已经超时
        boolean compTimeout = timeout2 > o.timeout;     //指明比对对象是否已经超时

        //当前对象的剩余时间大于比对对象的剩余时间(说明当前对象还有更长的时间才会超时)
        boolean timeoutComp = (this.timeout-timeout1) > (o.timeout-timeout2);

        //两个都已经超时
        if(thisTimeout && compTimeout) {
            if(timeoutComp) {
                return -1;
            } else {
                return 1;
            }
        } else if(thisTimeout) { //当前对象超时
            return -1;
        } else if(compTimeout) { //比对对象超时
            return 1;
        } else { //两个都未超时
            if(timeoutComp) return 1;
            else return -1;
        }

    }

    /**
     * 返回创建定时任务的时间 毫秒
     * @return
     */
    public long getCreateTime(){
        return createTime;
    }

    /**
     * 返回指定的超时时间
     * @return
     */
    public long getTimeout() {
        return timeout;
    }
}
