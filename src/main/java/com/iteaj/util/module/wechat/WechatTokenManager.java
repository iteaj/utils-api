package com.iteaj.util.module.wechat;

import com.iteaj.util.core.UtilsException;
import com.iteaj.util.core.UtilsType;
import com.iteaj.util.core.task.TimeoutTask;
import com.iteaj.util.core.task.TimeoutTaskManager;
import com.iteaj.util.module.wechat.basictoken.WechatBasicTokenApi;
import com.iteaj.util.module.wechat.basictoken.WechatBasicTokenApiConfig;

import java.util.concurrent.TimeUnit;

/**
 * Create Date By 2018-04-13
 *
 * @author iteaj
 * @since 1.7
 */
public class WechatTokenManager {

    private int count = 3;
    private int cyclicTimes = 0; //重新获取token的时间
    private WechatBasicTokenApi basicTokenApi;
    private static WechatTokenManager tokenManager;
    private WechatBasicTokenApi.BasicToken basicToken;
    protected WechatTokenManager(WechatBasicTokenApi basicTokenApi) {
        this.basicTokenApi = basicTokenApi;
    }

    public WechatBasicTokenApi.BasicToken getToken() {
        return basicToken;
    }

    public static WechatTokenManager build(String appId, String appSecret) {
        return build(new WechatBasicTokenApiConfig(appId, appSecret));
    }

    public static WechatTokenManager build(WechatBasicTokenApiConfig apiConfig) {
        return build(apiConfig.buildApi());
    }

    public static WechatTokenManager build(WechatBasicTokenApi basicTokenApi) {
        if(null != tokenManager) return tokenManager;

        synchronized (WechatTokenManager.class) {
            if(null != tokenManager) return tokenManager;

            return tokenManager = new WechatTokenManager(basicTokenApi).initTokenManager();
        }
    }

    protected WechatTokenManager initTokenManager() {
        int count = this.count;
        do {
            count --;
            this.basicToken = basicTokenApi.invoke(null);
            if(count <= 0) throw new UtilsException("获取微信的BasicToken失败", UtilsType.WECHAT);
        } while (!this.basicToken.success());

        Integer expires = Integer.valueOf(basicToken.getExpires_in());
        //如果没有指定超时时间, 则按0.6来算
        if(cyclicTimes == 0) {
            double v = expires * 0.6;
            cyclicTimes = new Double(v).intValue();
        } else {
            //如果指定的循环时间大于过期时间, 则按过期时间算
            if(cyclicTimes > expires) {
                cyclicTimes = expires;
            }
        }

        TimeoutTaskManager.instance().addTask(new TimeoutTask(cyclicTimes, TimeUnit.SECONDS) {
            @Override
            public void run() {
                initTokenManager();
            }
        });

        return this;
    }

    public WechatBasicTokenApi getBasicTokenApi() {
        return basicTokenApi;
    }

    public void setBasicTokenApi(WechatBasicTokenApi basicTokenApi) {
        this.basicTokenApi = basicTokenApi;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
