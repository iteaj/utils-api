package com.iteaj.util.module.aop.record;

import com.iteaj.util.module.aop.ActionRecord;
import com.iteaj.util.module.aop.RecordType;
import com.iteaj.util.module.aop.WeaveAction;
import com.iteaj.util.module.aop.factory.time.TimeWeaveActionFactory;

import java.util.Date;

/**
 * Create Date By 2016/10/28
 *  <p>时间记录,保存每个被织入的方法的执行时间</p>
 *  此记录对应的动作工厂{@link TimeWeaveActionFactory},
 *  每个被监控到的方法的执行时间都会被记录在此
 * @author iteaj
 * @since 1.7
 */
public class TimeRecord implements ActionRecord {

    private long time;
    private Date date;
    private RecordType recordType;
    private WeaveAction action;

    public TimeRecord() {
        this(null, 0);
    }

    /**
     *
     * @param action
     * @param time  被监控方法执行耗时
     */
    public TimeRecord(WeaveAction action, long time) {
        this.time = time;
        this.action = action;
        this.date = new Date();
        this.recordType = RecordType.Time;
    }

    @Override
    public String getId() {
        return recordType.name();
    }

    @Override
    public String generate() {
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"time(ms)\":").append(getTime()).append(',')
                .append("\"id\":\"").append(getId()).append("\",")
                .append("\"desc\":\"").append(getDesc()).append("\",")
                .append("\"class\":\"").append(getAction().getTarget().getName()).append("\",")
                .append("\"method\":\"").append(getAction().getMethod().getName()).append("\",")
                .append("\"dateTime\":\"").append(getDate()).append("\"")
                .append("}");
        return sb.toString();
    }

    @Override
    public String getDesc() {
        return recordType.getName();
    }

    public void setRecordType(RecordType recordType) {
        this.recordType = recordType;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public WeaveAction getAction() {
        return action;
    }

    public void setAction(WeaveAction action) {
        this.action = action;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("耗时(ms)：").append(time).append("-")
                .append("执行时间：").append(getDate()).append("-")
                .append("执行方法：").append(action.getTarget().getName()).append('.')
                .append(action.getMethod().getName());
        return sb.toString();
    }
}
