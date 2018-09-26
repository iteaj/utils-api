package com.iteaj.util.module.wechat.basictoken;

import com.iteaj.util.module.wechat.WechatExpires;

/**
 * create time: 2018/4/14
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class BasicToken extends WechatExpires {

    private String access_token;

    public static BasicToken ErrBasicToken = new BasicToken() {
        @Override
        public boolean success() {
            return false;
        }

        @Override
        public String getErrmsg() {
            return "未知的失败原因";
        }

        @Override
        public Integer getErrcode() {
            return -999999;
        }
    };

    public BasicToken() {

    }

    public BasicToken(String errmsg, Integer errcode) {
        this.setErrmsg(errmsg);
        this.setErrcode(errcode);
    }

    /**
     * 微信访问令牌
     * @return
     */
    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    @Override
    protected void setInvokeTime(long invokeTime) {
        super.setInvokeTime(invokeTime);
    }

    @Override
    protected boolean isExpires(int salt) {
        return super.isExpires(salt);
    }
}
