package com.iteaj.util;

import com.iteaj.util.module.wechat.authhorize.WechatWebAuthorizeApi;
import com.iteaj.util.module.wechat.basictoken.WechatBasicToken;
import com.iteaj.util.module.wechat.basictoken.WechatBasicTokenConfig;

/**
 * Create Date By 2018-04-03
 *
 * @author iteaj
 * @since 1.7
 */
public class UtilsTest {

    public static void main(String[] args) {
        String tet = "{\"openid\":\"oT2k_v-ghoTBVw-mvoN6iwCjdDyw\",\"nickname\":\"倾城\uE41D\",\"sex\":1,\"language\":\"zh_CN\",\"city\":\"厦门\",\"province\":\"福建\",\"country\":\"中国\",\"headimgurl\":\"http:\\/\\/thirdwx.qlogo.cn\\/mmopen\\/vi_32\\/VqC1ROPKSrfzY4Gyw2MlJwCJ5EeX0ic7H487FkPrMaBOFXdOaX5lZibFVtN85SH0FAjicia0bDQ68qIa6qZa8ia5jFw\\/132\",\"privilege\":[]}";

        WechatWebAuthorizeApi.UserInfo userInfo = JsonUtils.toBean(tet, WechatWebAuthorizeApi.UserInfo.class);
        System.out.println(userInfo);
    }
}
