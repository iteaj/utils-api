package com.iteaj.util.module.wechat;

import java.util.concurrent.TimeUnit;

/**
 * create time: 2018/9/24
 *
 * @author iteaj
 * @since 1.0
 */
public class WechatExpires extends WechatApiReturn {

    private int expires_in;
    private long invokeTime;

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    protected long getInvokeTime() {
        return invokeTime;
    }

    protected void setInvokeTime(long invokeTime) {
        this.invokeTime = invokeTime;
    }

    /**
     * 是否过期
     * @param salt 秒 可以调整的时间范围  必须大于0
     * @return
     */
    protected boolean isExpires(int salt) {
        long currentTimeMillis = System.currentTimeMillis();
        salt = salt < 0 ? 0 : salt;
        return currentTimeMillis - getInvokeTime()
                > (TimeUnit.SECONDS.toMillis(getExpires_in())
                - TimeUnit.SECONDS.toMillis(salt));
    }
}
