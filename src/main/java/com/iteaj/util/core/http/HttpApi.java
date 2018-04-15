package com.iteaj.util.core.http;

import com.iteaj.util.core.ApiParam;
import com.iteaj.util.core.UtilsApi;

/**
 * create time: 2018/4/13
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public interface HttpApi<P extends ApiParam> extends UtilsApi<P> {

    @Override
    HttpApiConfig getApiConfig();
}
