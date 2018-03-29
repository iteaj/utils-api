package com.iteaj.util.module.sms.smsBao;

import com.iteaj.util.module.sms.SendStatus;

/**
 * Create Date By 2016/9/27
 *  短信宝的返回值信息
 * @author iteaj
 * @since 1.7
 */
public class SmsBaoSendStatus implements SendStatus {

    private Integer status;

    public SmsBaoSendStatus(Integer status){
        this.status = status;
    }

    @Override
    public boolean isSuccess() {
        return status != null && status == 0;
    }

    @Override
    public Integer getStatus() {
        return status;
    }

    @Override
    public String getErrMsg() {
        switch (status){
            case 0: return "成功";
            case 30: return "密码错误";
            case 40: return "账号不存在";
            case 41: return "余额不足";
            case 42: return "帐号过期";
            case 43: return "IP地址限制";
            case 50: return "内容含有敏感词";
            case 51: return "手机号码不正确";
            default: return "未知错误";
        }
    }
}
