package com.iteaj.util.wechat;

import com.alibaba.fastjson.annotation.JSONField;
import com.iteaj.util.JsonUtils;
import com.iteaj.util.module.authorization.MapStorageContext;
import com.iteaj.util.http.HttpAdapter;
import com.iteaj.util.json.JsonFactory;
import com.iteaj.util.json.JsonWrapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Create Date By 2018-03-07
 *
 * @author iteaj
 * @since 1.7
 */
public class WechatTemplateMessage {

    //模版跳转url
    private String url;
    //发送消息网关
    private String gateway;
    private String templateId;  //模板ID
    private String miniprogram;	//否	跳小程序所需数据，不需跳小程序可不用传该数据
    private String appid;  //	是	所需跳转到的小程序appid（该小程序appid必须与发模板消息的公众号是绑定关联关系）
    private String pagepath;   //	是	所需跳转到小程序的具体页面路径，支持带参数,（示例index?foo=bar）
    private String color;   //	否	模板内容字体颜色，不填默认为黑色
    private Logger logger = LoggerFactory.getLogger(getClass());
    private HttpAdapter http;
    private WechatAccessToken wechatAccessToken;

    public WechatTemplateMessage() {
        this.color = "#000000";
        this.gateway = "https://api.weixin.qq.com/cgi-bin/message/template/send";
    }

    /**
     *  对于{@code items} 此集合存储顺序必须和微信内容项对应
     *  第一项是：first  第二项是：remark
     * @param openId
     * @param items
     * @return
     */
    public Response send(String openId, List<Item> items){
        if(null == items || StringUtils.isBlank(openId))
            throw new IllegalArgumentException("发送的参数错误");
        try {
            WechatAccessToken.BasicToken invoke = wechatAccessToken.invoke(new MapStorageContext());
            if(!invoke.success()) throw new IllegalStateException("获取微信AccessToken失败："+invoke.getErrmsg());

            JsonWrapper json = JsonFactory.create();
            if(StringUtils.isNotBlank(url))json.put("url", url);
            if(StringUtils.isNotBlank(appid) && StringUtils.isNotBlank(pagepath)){
                JsonWrapper build = JsonFactory.create();
                build.put("appid", appid)
                        .put("pagepath", pagepath)
                        .put("miniprogram", miniprogram);
            }

            JsonWrapper data = JsonFactory.create();
            for(Item item : items){
                data.put(item.key, item);
            }

            json.put("data", data);
            json.put("touser", openId);
            json.put("template_id", templateId);

            if(logger.isDebugEnabled())
                logger.debug("类别：微信接口 - 动作：发送模版消息 - 描述：发送报文 {} - token：{}"
                        , json.toJsonString(), invoke.getAccess_token());

//            byte[] bytes = HttpClient4Util.getInstance().doPost(this.gateway + "?access_token="
//                            + invoke.getAccess_token(), null
//                    , new StringEntity(jsonObject.toJSONString(), Charset.forName("utf-8")));

            byte[] bytes = http.post();


            return JsonUtils.toBean(new String(bytes, "utf-8"), Response.class);
        } catch (Exception e) {
            throw new IllegalStateException("发送微信模版消息失败：", e);
        }
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

    public static class Response {

        private String msgid;
        private String errmsg;
        private String errcode;

        public String getMsgid() {
            return msgid;
        }

        public void setMsgid(String msgid) {
            this.msgid = msgid;
        }

        public String getErrmsg() {
            return errmsg;
        }

        public void setErrmsg(String errmsg) {
            this.errmsg = errmsg;
        }

        public String getErrcode() {
            return errcode;
        }

        public void setErrcode(String errcode) {
            this.errcode = errcode;
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getMiniprogram() {
        return miniprogram;
    }

    public void setMiniprogram(String miniprogram) {
        this.miniprogram = miniprogram;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPagepath() {
        return pagepath;
    }

    public void setPagepath(String pagepath) {
        this.pagepath = pagepath;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public WechatAccessToken getWechatAccessToken() {
        return wechatAccessToken;
    }

    public void setWechatAccessToken(WechatAccessToken wechatAccessToken) {
        this.wechatAccessToken = wechatAccessToken;
    }
}
