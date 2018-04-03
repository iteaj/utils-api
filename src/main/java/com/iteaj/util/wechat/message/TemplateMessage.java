package com.iteaj.util.wechat.message;

import com.iteaj.util.*;
import com.iteaj.util.http.build.SimpleBuilder;
import com.iteaj.util.json.JsonFactory;
import com.iteaj.util.json.JsonWrapper;
import com.iteaj.util.wechat.WechatApi;
import com.iteaj.util.wechat.basictoken.WechatBasicToken;
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
public class TemplateMessage implements WechatApi<WechatTemplateMessageConfig, TemplateMessageParam> {


    private WechatBasicToken wechatAccessToken;
    private WechatTemplateMessageConfig config;
    private Logger logger = LoggerFactory.getLogger(getClass());

    protected TemplateMessage(WechatTemplateMessageConfig messageConfig) {
        this.config = messageConfig;
    }

    @Override
    public WechatTemplateMessageConfig getConfig() {
        return config;
    }

    @Override
    public MessageResponse invoke(TemplateMessageParam param) {
        AssertUtil.isTrue(null != param, "微信模版消息 - 参数错误", UtilsType.WECHAT);
        AssertUtil.isNotBlank(param.getOpenId(), "微信模版消息 - 未指定接受者的openId", UtilsType.WECHAT);
        AssertUtil.isNotBlank(param.getTemplateId(), "微信模版消息 - 未指定模版的templateId", UtilsType.WECHAT);
        AssertUtil.isTrue(CommonUtils.isNotEmpty(param.getItems()), "微信模版消息 - 未模版数据项", UtilsType.WECHAT);

        try {
            WechatBasicToken.BasicToken invoke = wechatAccessToken.invoke(UtilsApi.VOID_PARAM);
            if(!invoke.success()) throw new IllegalStateException("获取微信AccessToken失败："+invoke.getErrmsg());

            JsonWrapper json = JsonFactory.createJson();
            if(StringUtils.isNotBlank(param.getUrl()))json.addNode("url", param.getUrl());
            if(StringUtils.isNotBlank(config.getAppId())
                    && StringUtils.isNotBlank(param.getPagepath())){
                JsonWrapper build = JsonFactory.createJson();
                build.addNode("appid", appid)
                        .addNode("pagepath", pagepath)
                        .addNode("miniprogram", miniprogram);
            }

            JsonWrapper data = JsonFactory.createJson();
            for(Item item : items){
                data.addNode(item.key, item);
            }

            json.addNode("data", data);
            json.addNode("touser", openId);
            json.addNode("template_id", templateId);

            if(logger.isDebugEnabled())
                logger.debug("类别：微信接口 - 动作：发送模版消息 - 描述：发送报文 {} - token：{}"
                        , json.toJsonString(), invoke.getAccess_token());

            SimpleBuilder builder = SimpleBuilder.build(this.gateway);
            builder.addParam("access_token", invoke.getAccess_token())
                    .addBody(json.toJsonString());

            String result = HttpUtils.doPost(builder, "utf-8");
            return JsonUtils.toBean(result, MessageResponse.class);
        } catch (Exception e) {
            throw new IllegalStateException("发送微信模版消息失败：", e);
        }

        return null;
    }

    public static class MessageResponse {

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


    public WechatBasicToken getWechatAccessToken() {
        return wechatAccessToken;
    }

    public void setWechatAccessToken(WechatBasicToken wechatAccessToken) {
        this.wechatAccessToken = wechatAccessToken;
    }
}
