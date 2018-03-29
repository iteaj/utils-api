package com.iteaj.util.module.sms;

/**
 * Create Date By 2016/9/7
 *  短信实体
 * @author iteaj
 * @since 1.7
 */
public class SmsEntity {

    private String[] phones;
    private String content;

    public SmsEntity(String content, String... phones) {
        this.phones = phones;
        this.content = content;
    }

    public String[] getPhones() {
        return phones;
    }

    public void setPhones(String[] phones) {
        this.phones = phones;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
