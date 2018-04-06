package com.iteaj.util;

/**
 * Create Date By 2018-04-03
 *    所有api接口的基类
 * @author iteaj
 * @since 1.7
 */
public interface UtilsApi<P extends UtilsApi.ApiParam> {

    VoidApiParam VOID_PARAM = new VoidApiParam();

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

    /**
     *  调用方法是所需要的参数
     */
    interface ApiParam {

    }

    /**
     * 指调用此次Api无需传任何参数
     * @see #invoke(ApiParam)
     */
    class VoidApiParam implements ApiParam {

    }
}
