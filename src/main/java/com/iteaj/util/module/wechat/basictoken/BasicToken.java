package com.iteaj.util.module.wechat.basictoken;

import com.iteaj.util.module.wechat.WechatApiResponse;

/**
 * create time: 2018/4/14
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class BasicToken extends WechatApiResponse {

    private Integer expires_in;
    private String access_token;

    public BasicToken() {

    }

    public BasicToken(String errmsg, Integer errcode) {
        this.setErrmsg(errmsg);
        this.setErrcode(errcode);
    }

    /**
     * access_token过期时间
     * @return
     */
    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
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
    public String toString() {
        return "BasicToken{" +
                "errcode='" + getErrcode() + '\'' +
                ", errmsg='" + getErrmsg() + '\'' +
                ", expires_in='" + expires_in + '\'' +
                ", access_token='" + access_token + '\'' +
                '}';
    }
}
