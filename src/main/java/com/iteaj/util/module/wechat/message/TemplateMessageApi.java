package com.iteaj.util.module.wechat.message;

import com.iteaj.util.AssertUtils;
import com.iteaj.util.CommonUtils;
import com.iteaj.util.HttpUtils;
import com.iteaj.util.JsonUtils;
import com.iteaj.util.core.UtilsException;
import com.iteaj.util.core.UtilsType;
import com.iteaj.util.module.http.build.StreamBuilder;
import com.iteaj.util.module.json.Json;
import com.iteaj.util.module.wechat.AbstractWechatApi;
import com.iteaj.util.module.wechat.WechatApiResponse;
import com.iteaj.util.module.wechat.WechatApiType;
import com.iteaj.util.module.wechat.basictoken.BasicToken;
import com.iteaj.util.module.wechat.basictoken.WechatConfigBasicToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create Date By 2018-03-07
 *
 * @author iteaj
 * @since 1.7
 */
public class TemplateMessageApi extends AbstractWechatApi
        <WechatConfigTemplateMessage, WechatParamTemplateMessage> {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public TemplateMessageApi(WechatConfigTemplateMessage config) {
        super(config);
    }

    @Override
    public MessageResponse invoke(WechatParamTemplateMessage param) {
        AssertUtils.isTrue(null != param, "微信模版消息 - 参数错误", UtilsType.WECHAT);
        AssertUtils.isNotBlank(param.getOpenId(), "微信模版消息 - 未指定接受者的openId", UtilsType.WECHAT);
        AssertUtils.isNotBlank(param.getTemplateId(), "微信模版消息 - 未指定模版的templateId", UtilsType.WECHAT);
        AssertUtils.isTrue(CommonUtils.isNotEmpty(param.getItems()), "微信模版消息 - 无模版数据项", UtilsType.WECHAT);

        try {
            WechatConfigBasicToken tokenConfig = new WechatConfigBasicToken
                    (getApiConfig().getAppId(), getApiConfig().getAppSecret());

            BasicToken invoke = getApiConfig().getTokenManager().getToken(tokenConfig);
            if(!invoke.success()) throw new IllegalStateException("获取微信AccessToken失败："+invoke.getErrmsg());

            Json json = JsonUtils.buildJson();
            if(CommonUtils.isNotBlank(param.getUrl()))json.addNode("url", param.getUrl());
            if(CommonUtils.isNotBlank(param.getMiniprogram())
                    && CommonUtils.isNotBlank(param.getPagepath())){

                Json build = JsonUtils.buildJson();
                build.addNode("appid", getApiConfig().getAppId())
                        .addNode("pagepath", param.getPagepath());

                json.addNode("miniprogram", build);
            }

            Json data = JsonUtils.buildJson();
            for(WechatParamTemplateMessage.Item item : param.getItems()){
                data.addNode(item.getKey(), item);
            }

            json.addNode("data", data);
            json.addNode("touser", param.getOpenId());
            json.addNode("template_id", param.getTemplateId());

            String message = json.toJsonString();
            if(logger.isDebugEnabled())
                logger.debug("类别：微信接口 - 动作：发送模版消息 - 描述：发送报文 {} - token：{}"
                        , message, invoke.getAccess_token());

            StreamBuilder builder = StreamBuilder.build(getApiConfig().getApiGateway());
            builder.addParam("access_token", invoke.getAccess_token()).setForPlain(message);

            String result = HttpUtils.doPost(builder, "utf-8");
            MessageResponse response = JsonUtils.toBean(result, MessageResponse.class);
            if(!response.success()) {
                //如果返回是40001错误 则强制刷新一次Token
                if(response.getErrcode() == 40001)
                    getApiConfig().getTokenManager().refresh(tokenConfig);
            }
            return response;
        } catch (Exception e) {
            throw new UtilsException("发送微信模版消息失败：", e, UtilsType.WECHAT);
        }

    }

    @Override
    public WechatApiType getApiType() {
        return WechatApiType.TemplateMessage;
    }

    public static class MessageResponse extends WechatApiResponse {

        private String msgid;

        public String getMsgid() {
            return msgid;
        }

        public void setMsgid(String msgid) {
            this.msgid = msgid;
        }
    }

}
