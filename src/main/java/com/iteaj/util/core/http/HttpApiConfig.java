package com.iteaj.util.core.http;

import com.iteaj.util.core.ApiConfig;

/**
 * create time: 2018/4/13
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public interface HttpApiConfig extends ApiConfig {

    /**
     * 返回调用Api的网关
     * e.g.  http://www.iteaj.com
     * @return
     */
    String getApiGateway();

    /**
     * 设置Api接口的网关
     * @param gateway
     */
    void setApiGateway(String gateway);
}
