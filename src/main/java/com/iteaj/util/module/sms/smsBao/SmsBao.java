package com.iteaj.util.module.sms.smsBao;

import com.iteaj.util.module.sms.Sms;
import com.iteaj.util.module.sms.SmsConfigure;
import com.iteaj.util.module.sms.SmsEntity;
import com.iteaj.util.module.util.EncryptSecurity;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.net.URLEncoder;

/**
 * Create Date By 2016/9/7
 *  短信宝短信接口
 * @author iteaj
 * @since 1.7
 */
public class SmsBao implements Sms<SmsBaoSendStatus> {

    private static Sms sms;
    /**短信接口的各项配置信息是否验证通过*/
    private boolean validated;
    /**短信配置*/
    private SmsConfigure configure;
    /**短信安全相关*/
    private EncryptSecurity security;

    public static Sms instance(SmsConfigure configure){
        if(null == sms)
            return new SmsBao(configure);

        return sms;
    }

    private SmsBao(SmsConfigure configure) {
        this.configure = configure;
        this.security = new SmsMd5();
    }

    @Override
    public SmsBaoSendStatus send(SmsEntity entity) {
        return send(entity.getContent(),entity.getPhones());
    }

    @Override
    public SmsBaoSendStatus send(String content, String... phones) {
        try {
            //验证短信配置信息
            validateConfigure();

            //组装要发送的内容
            String sendContent = assembleSendContent(content, phones);

            byte[] bytes = null;//HttpClient4Util.getInstance().doPost(configure.getGateway()
//                    , null, new StringEntity(sendContent));

            return getSendStatus(bytes);
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public SmsConfigure getConfigure() {
        return configure;
    }

    private SmsBaoSendStatus getSendStatus(byte[] bytes) {
        Integer valueOf = Integer.valueOf(new String(bytes));
        return new SmsBaoSendStatus(valueOf);
    }

    private void validateConfigure() {
        if(validated) return;

        if(null==configure)
            throw new IllegalArgumentException("未设置短信接口配置信息：SmsConfigure");

        if(StringUtils.isBlank(configure.getUserName()) ||
                StringUtils.isBlank(configure.getPassword()))
            throw new IllegalArgumentException("短信接口配置：用户名或密码为空");

        this.validated = true;
    }

    /**
     * 组装要发送的内容
     * @param content
     * @param phones
     * @return
     */
    private String assembleSendContent(String content, String... phones){
        StringBuffer httpArg = new StringBuffer();
        httpArg.append("u=").append(configure.getUserName()).append("&");
        httpArg.append("p=").append(security.encrypt(configure.getPassword())).append("&");
        httpArg.append("m=").append(assemblePhones(phones)).append("&");
        httpArg.append("c=").append(UrlEncode(content));
        return httpArg.toString();
    }

    private String assemblePhones(String... phones){
        StringBuffer sb = new StringBuffer();

        if(ArrayUtils.isEmpty(phones))
            throw new IllegalArgumentException("警告：此短信未指定号码,发送失败");

        for(String item : phones){
            if(StringUtils.isBlank(item))
                throw new IllegalArgumentException("警告：此短信指定的号码列表错误,发送失败");

            sb.append(item).append(',');
        }

        return sb.substring(0,sb.length()-1);
    }

    private String UrlEncode(String content){
        if (content == null) return content;
        try {
            return URLEncoder.encode(content, configure.getEncode());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public EncryptSecurity getSecurity() {
        return security;
    }

    public void setSecurity(EncryptSecurity security) {
        this.security = security;
    }
}
