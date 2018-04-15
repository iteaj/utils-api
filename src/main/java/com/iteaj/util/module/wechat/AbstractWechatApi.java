package com.iteaj.util.module.wechat;

import com.iteaj.util.CommonUtils;

import java.lang.reflect.Type;

/**
 * create time: 2018/4/6
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public abstract class AbstractWechatApi<C extends WechatConfig
        , P extends WechatApiParam> implements WechatApi<C, P> {

    private C config;

    public AbstractWechatApi(C config) {
        this.config = config;
    }

    @Override
    public String desc() {
        return getApiType().desc;
    }

    @Override
    public C getApiConfig() {
        return config;
    }

    @Override
    public void setApiConfig(C config) {
        this.config = config;
    }

    @Override
    public Class<P> getParamType() {
        Type[] types = CommonUtils.getParameterizedType(this);
        if(CommonUtils.isNotEmpty(types))
            return (Class<P>) types[1];

        return null;
    }
}
