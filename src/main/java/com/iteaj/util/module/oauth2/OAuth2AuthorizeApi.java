package com.iteaj.util.module.oauth2;

import com.iteaj.util.UtilsApi;

/**
 * create time: 2018/4/5
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public abstract class OAuth2AuthorizeApi<R extends OAuth2ApiConfig, C extends OAuth2ApiParam>
        extends AbstractAuthorizeType<C> implements UtilsApi<C> {

    private R apiConfig;

    public OAuth2AuthorizeApi(R apiConfig) {
        this.apiConfig = apiConfig;
    }

    @Override
    public R getApiConfig() {
        return apiConfig;
    }

    public void setApiConfig(R apiConfig) {
        this.apiConfig = apiConfig;
    }
}
