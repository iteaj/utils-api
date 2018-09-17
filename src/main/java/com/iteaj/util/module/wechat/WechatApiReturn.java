package com.iteaj.util.module.wechat;

import com.iteaj.util.core.ApiReturn;

/**
 * create time: 2018/4/15
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public abstract class WechatApiReturn implements ApiReturn {

    private String errmsg;
    private Integer errcode;

    public boolean success() {
        if(errcode == null)
            return true;

        return 0 == errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }
}
