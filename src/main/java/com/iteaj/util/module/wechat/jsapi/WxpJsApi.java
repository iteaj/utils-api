package com.iteaj.util.module.wechat.jsapi;

import com.iteaj.util.AssertUtils;
import com.iteaj.util.core.UtilsType;
import com.iteaj.util.module.wechat.WechatApiParam;

/**
 * create time: 2018/6/17
 * @see WxaJsApi#invoke(WxpJsApi)
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class WxpJsApi implements WechatApiParam<WxcJsApi.WxrJsApi> {

    private String url;

    public WxpJsApi(String url) {
        this.url = url;
        AssertUtils.isNotBlank(url, "未指定参数:微信JsApi", UtilsType.WECHAT);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
