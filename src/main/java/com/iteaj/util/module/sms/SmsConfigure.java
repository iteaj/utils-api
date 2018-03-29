package com.iteaj.util.module.sms;

import com.iteaj.util.PropertyUtils;

/**
 * Create Date By 2016/9/7
 *  短信接口配置
 * @author iteaj
 * @since 1.7
 */
public class SmsConfigure {

    /**短信接口网关*/
    private String gateway;

    /**用户名*/
    private String userName;
    /**密码*/
    private String password;

    /**内容编码*/
    private String encode;
    /**是否异步发送*/
    private boolean async;

    public SmsConfigure(){
        this.encode = "utf-8";
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }
}
