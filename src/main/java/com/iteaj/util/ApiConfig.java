package com.iteaj.util;

/**
 * Create Date By 2018-04-03
 *
 * @author iteaj
 * @since 1.7
 */
public interface ApiConfig {

    /**
     * 返回此配置的类型
     * @see UtilsType 详细的类型
     * @return
     */
    UtilsType getUtilsType();

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
