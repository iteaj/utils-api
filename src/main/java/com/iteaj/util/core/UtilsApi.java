package com.iteaj.util.core;

/**
 * Create Date By 2018-04-03
 *    所有api接口的基类
 * @author iteaj
 * @since 1.7
 */
public interface UtilsApi<P extends ApiParam> {

    /**
     * 返回指定api接口的   配置
     * @return
     */
    ApiConfig getApiConfig();

    /**
     * 调用接口
     * @param param 调用所需的参数
     * @return
     */
    Object invoke(P param) throws UtilsException;

}
