package com.iteaj.util.module.wechat.jsapi;

import com.iteaj.util.DigestUtils;
import com.iteaj.util.RandomUtils;
import com.iteaj.util.core.UtilsException;
import com.iteaj.util.core.UtilsType;
import com.iteaj.util.module.wechat.WechatApiReturn;
import com.iteaj.util.module.wechat.WechatConfig;

import java.io.UnsupportedEncodingException;

/**
 * create time: 2018/6/17
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class WxcJsApi extends WechatConfig<WxaJsApi> {

    public WxcJsApi(String appId, String appSecret) {
        super(appId, appSecret, null);
    }

    @Override
    public WxaJsApi buildApi() {
        return new WxaJsApi(this);
    }

    public class WxrJsApi extends WechatApiReturn {
        private String appId;
        private long timestamp;
        private String nonceStr;
        private String signature;

        public WxrJsApi() {
            this.appId = WxcJsApi.this.getAppId();
            this.timestamp = System.currentTimeMillis()/1000;
            this.nonceStr = RandomUtils.create(16, RandomUtils.Type.L26);
        }

        public WxrJsApi build(String url, String ticket) {
            try {
                WxrJsApi wxrJsApi = new WxrJsApi();

                StringBuffer buffer = new StringBuffer();
                buffer.append("jsapi_ticket=").append(ticket).append("&")
                        .append("noncestr=").append(wxrJsApi.nonceStr).append("&")
                        .append("timestamp=").append(wxrJsApi.timestamp).append("&")
                        .append("url=").append(url);

                byte[] bytes = buffer.toString().getBytes("UTF-8");
                wxrJsApi.setSignature(DigestUtils.sha1Hex(bytes));

                return wxrJsApi;
            } catch (UnsupportedEncodingException e) {
                throw new UtilsException(e.getMessage(), e, UtilsType.WECHAT);
            }
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public String getNonceStr() {
            return nonceStr;
        }

        public void setNonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }
    }
}
