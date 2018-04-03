package com.iteaj.util.wechat.message;

import com.alibaba.fastjson.annotation.JSONField;
import com.iteaj.util.UtilsApi;

import java.util.ArrayList;
import java.util.List;

/**
 * Create Date By 2018-04-03
 *
 * @author iteaj
 * @since 1.7
 */
public class TemplateMessageParam implements UtilsApi.ApiParam {

    //模版跳转url
    private String url;
    private String templateId;  //  是  模板ID
    private String miniprogram;	//  否	跳小程序所需数据，不需跳小程序可不用传该数据
    private String pagepath;    //	是	所需跳转到小程序的具体页面路径，支持带参数,（示例index?foo=bar）
    private String color;       //	否	模板内容字体颜色，不填默认为黑色

    private String openId; //发送的用户的openId

    private List<Item> items; //微信消息模版的Data数据

    public TemplateMessageParam(String openId, String templateId) {
        this.openId = openId;
        this.templateId = templateId;
        this.items = new ArrayList<>();
    }

    /**
     * 增加一条模版消息项
     * @param key
     * @param val
     * @return
     */
    public TemplateMessageParam addItem(String key, String val) {
        return this.addItem(key, val, null);
    }

    /**
     * 增加一条模版消息项
     * @param key
     * @param val
     * @param color
     * @return
     */
    public TemplateMessageParam addItem(String key, String val, String color) {
        this.items.add(new Item(key, val, color));
        return this;
    }

    public String getUrl() {
        return url;
    }

    public TemplateMessageParam setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getTemplateId() {
        return templateId;
    }

    public TemplateMessageParam setTemplateId(String templateId) {
        this.templateId = templateId;
        return this;
    }

    public String getMiniprogram() {
        return miniprogram;
    }

    public TemplateMessageParam setMiniprogram(String miniprogram) {
        this.miniprogram = miniprogram;
        return this;
    }

    public String getPagepath() {
        return pagepath;
    }

    public TemplateMessageParam setPagepath(String pagepath) {
        this.pagepath = pagepath;
        return this;
    }

    public String getColor() {
        return color;
    }

    public TemplateMessageParam setColor(String color) {
        this.color = color;
        return this;
    }

    public String getOpenId() {
        return openId;
    }

    public List<Item> getItems() {
        return items;
    }

    /**
     * 微信消息模版的Data数据的一个项
     * e.g. {"first":{"value":"aa","color":"#bbbbbb"}}
     */
    public static class Item {
        private String key; //Json格式的key   比如：first, remark
        private String value; //key下面的值{"value":"aa"}
        private String color; //key下面的值{"color":"#bbbbbb"}

        public Item(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public Item(String key, String value, String color) {
            this.key = key;
            this.value = value;
            this.color = color;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        @JSONField(serialize = false)
        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}
