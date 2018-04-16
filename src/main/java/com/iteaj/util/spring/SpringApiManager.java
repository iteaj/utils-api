package com.iteaj.util.spring;

import com.iteaj.util.core.ApiInvokeReturn;
import com.iteaj.util.core.ApiParam;
import com.iteaj.util.core.UtilsApi;

/**
 * create time: 2018/4/14
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public interface SpringApiManager {


    /**
     * 通过api所需的调用参数获取api
     * @param clazz
     * @return
     */
    <P extends ApiParam> UtilsApi getApi(Class<P> clazz);

    /**
     * 根据调用api所需的参数直接调用api
     * @param param
     * @param <T>
     * @return
     */
    <T extends ApiInvokeReturn> T invoke(ApiParam<T> param);
}
