package com.iteaj.util;

import com.iteaj.util.module.wechat.WechatApi;
import com.iteaj.util.module.wechat.WechatConfig;

/**
 * Create Date By 2018-04-03
 *  工具类构建器
 * @author iteaj
 * @since 1.7
 */
public class UtilsBuilder {

    /**
     * 构建微信Api接口
     * @param config    接口对应的配置
     * @param <T>       具体的微信Api类型
     * @return
     */
    public static <T extends WechatApi> T wechatApi(WechatConfig<T> config) {
        return config.buildApi();
    }
}
