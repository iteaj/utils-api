package com.iteaj.util.module.wechat;

import com.iteaj.util.core.ApiParam;
import com.iteaj.util.core.UtilsApi;

/**
 * Create Date By 2018-04-03
 *  用来标明是一个微信Api接口
 * @author iteaj
 * @since 1.7
 */
public interface WechatApi<C extends WechatConfig
        , P extends ApiParam> extends UtilsApi<P> {

    /**
     * 返回一个微信对应Api的配置
     * @return
     */
    C getApiConfig();

    /**
     * 设置接口配置配置
     * @param config
     */
    void setApiConfig(C config);
}
