package com.iteaj.util.module.wechat.message;

import com.iteaj.util.*;
import com.iteaj.util.core.UtilsType;
import com.iteaj.util.module.http.build.SimpleBuilder;
import com.iteaj.util.module.json.JsonWrapper;
import com.iteaj.util.module.wechat.AbstractWechatApi;
import com.iteaj.util.module.wechat.basictoken.WechatBasicToken;
import com.iteaj.util.module.wechat.basictoken.WechatBasicTokenConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create Date By 2018-03-07
 *
 * @author iteaj
 * @since 1.7
 */
public class TemplateMessageApi extends AbstractWechatApi
        <WechatTemplateMessageConfig, TemplateMessageParam> {


    private WechatBasicToken basicToken;
    private WechatTemplateMessageConfig config;
    private Logger logger = LoggerFactory.getLogger(getClass());

    protected TemplateMessageApi(WechatTemplateMessageConfig config) {
        this.config = config;
        this.basicToken = UtilsBuilder.wechatApi(new WechatBasicTokenConfig
                (config.getAppId(), config.getAppSecret()));
    }

    protected TemplateMessageApi(WechatBasicToken basicToken
            , WechatTemplateMessageConfig config) {
        this.basicToken = basicToken;
        this.config = config;
    }

    @Override
    public WechatTemplateMessageConfig getApiConfig() {
        return config;
    }

    @Override
    public void setApiConfig(WechatTemplateMessageConfig config) {
        this.config = config;
    }

    @Override
    public MessageResponse invoke(TemplateMessageParam param) {
        AssertUtils.isTrue(null != param, "微信模版消息 - 参数错误", UtilsType.WECHAT);
        AssertUtils.isNotBlank(param.getOpenId(), "微信模版消息 - 未指定接受者的openId", UtilsType.WECHAT);
        AssertUtils.isNotBlank(param.getTemplateId(), "微信模版消息 - 未指定模版的templateId", UtilsType.WECHAT);
        AssertUtils.isTrue(CommonUtils.isNotEmpty(param.getItems()), "微信模版消息 - 未模版数据项", UtilsType.WECHAT);

        try {
            WechatBasicToken.BasicToken invoke = basicToken.invoke(null);
            if(!invoke.success()) throw new IllegalStateException("获取微信AccessToken失败："+invoke.getErrmsg());

            JsonWrapper json = JsonUtils.buildJson();
            if(CommonUtils.isNotBlank(param.getUrl()))json.addNode("url", param.getUrl());
            if(CommonUtils.isNotBlank(config.getAppId())
                    && CommonUtils.isNotBlank(param.getPagepath())){
                JsonWrapper build = JsonUtils.buildJson();
                build.addNode("appid", config.getAppId())
                        .addNode("pagepath", param.getPagepath())
                        .addNode("miniprogram", param.getMiniprogram());
            }

            JsonWrapper data = JsonUtils.buildJson();
            for(TemplateMessageParam.Item item : param.getItems()){
                data.addNode(item.getKey(), item);
            }

            json.addNode("data", data);
            json.addNode("touser", param.getOpenId());
            json.addNode("template_id", param.getTemplateId());

            if(logger.isDebugEnabled())
                logger.debug("类别：微信接口 - 动作：发送模版消息 - 描述：发送报文 {} - token：{}"
                        , json.toJsonString(), invoke.getAccess_token());

            SimpleBuilder builder = SimpleBuilder.build(config.getApiGateway());
            builder.addParam("access_token", invoke.getAccess_token())
                    .addBody(json.toJsonString());

            String result = HttpUtils.doPost(builder, "utf-8");
            return JsonUtils.toBean(result, MessageResponse.class);
        } catch (Exception e) {
            throw new IllegalStateException("发送微信模版消息失败：", e);
        }

    }

    public static class MessageResponse {

        private String msgid;
        private String errmsg;
        private String errcode;

        public boolean success() {
            return "0".equals(errcode);
        }

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


    public WechatBasicToken getBasicToken() {
        return basicToken;
    }

    public void setBasicToken(WechatBasicToken basicToken) {
        this.basicToken = basicToken;
    }
}
