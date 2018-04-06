package com.iteaj.util.module.wechat.authhorize;

/**
 * Create Date By 2017-08-22
 *
 * @author iteaj
 * @since 1.7
 */
public abstract class WechatResultEntityAbstract {

    private String errcode;
    private String errmsg;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
