package com.iteaj.util.module.aop.record;

import com.iteaj.util.module.aop.ActionRecord;
import com.iteaj.util.module.aop.RecordType;
import com.iteaj.util.module.aop.WeaveAction;

import java.util.Date;

/**
 * Create Date By 2018-02-27
 *    此中记录无任何输出
 * @author iteaj
 * @since 1.7
 */
public class VoidRecord implements ActionRecord {

    private Date date;
    private RecordType type;
    private WeaveAction action;

    public VoidRecord() {
        this.date = new Date();
        this.type = RecordType.Void;
    }

    @Override
    public String getId() {
        return this.type.name();
    }

    @Override
    public String generate() {
        return null;
    }

    @Override
    public String getDesc() {
        return this.type.getName();
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public WeaveAction getAction() {
        return null;
    }
}
