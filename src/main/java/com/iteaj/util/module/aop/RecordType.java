package com.iteaj.util.module.aop;

/**
 * Create Date By 2016/11/17
 *
 * @author iteaj
 * @since 1.7
 */
public enum RecordType {

    Time(0,"时间记录"),Exception(1,"异常记录"), Void(2, "空记录");

    private int id;
    private String name;
    RecordType(int id,String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
