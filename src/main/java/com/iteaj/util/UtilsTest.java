package com.iteaj.util;

import com.iteaj.util.module.http.adapter.HttpClientAdapter;
import com.iteaj.util.module.http.adapter.HttpClientResponse;
import com.iteaj.util.module.http.adapter.JdkHttpAdapter;
import com.iteaj.util.module.http.adapter.JdkHttpResponse;
import com.iteaj.util.module.http.build.EntityBuilder;
import com.iteaj.util.module.http.build.TextBuilder;
import com.iteaj.util.module.http.build.UrlBuilder;
import com.iteaj.util.module.wechat.authhorize.WechatWebAuthorizeApi;
import com.iteaj.util.module.wechat.basictoken.WechatBasicToken;
import com.iteaj.util.module.wechat.basictoken.WechatBasicTokenConfig;
import com.iteaj.util.module.wechat.message.TemplateMessageApi;
import com.iteaj.util.module.wechat.message.TemplateMessageParam;
import com.iteaj.util.module.wechat.message.WechatTemplateMessageConfig;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Create Date By 2018-04-03
 *
 * @author iteaj
 * @since 1.7
 */
public class UtilsTest {

    private static String appId = "wx54d2245774779631";
    private static String openId = "oT2k_v-ghoTBVw-mvoN6iwCjdDyw";
    private static String appSecret = "ccc8e99e58a01457c9818ab557d31582";
    private static String templateId = "bnhMftmMZECqgnqZWgxxONIk-5SsdR2wBdTGL2gvJGU";

    @Test
    public void http() {
        /* ------------------------------UrlBuilder-------------------------------- */
        UrlBuilder urlBuilder = UrlBuilder.build("https://www.baidu.com/frame")
                .addHead("Accept", "iteaj/*").addParam("who", "iteaj");
        JdkHttpAdapter jdkHttpAdapter = new JdkHttpAdapter();
        JdkHttpResponse jdkHttpResponse = jdkHttpAdapter.get(urlBuilder);
        System.out.println(jdkHttpResponse.getContent("UTF-8"));

        HttpClientAdapter clientAdapter = HttpClientAdapter.instance();
        HttpClientResponse httpClientResponse = clientAdapter.get(urlBuilder);
        System.out.println(httpClientResponse.getContent("UTF-8"));
        /* -------------------------------TextBuilder------------------------------- */
        TextBuilder textBuilder = TextBuilder.build("http://www.iteaj.com/frame")
                .addText("<div>who=iteaj</div>");

        JdkHttpResponse post = jdkHttpAdapter.post(textBuilder);
        System.out.println(post.getContent("UTF-8"));

        HttpClientResponse clientResponse = clientAdapter.post(textBuilder);
        System.out.println(clientResponse.getContent("UTF-8"));

        /* --------------------------------EntityBuilder------------------------------ */
        EntityBuilder entityBuilder = EntityBuilder.build("http://www.iteaj.com/frame")
                .addBody("who", "iteaj").addBody("doing", "it");

        String content = jdkHttpAdapter.post(entityBuilder).getContent("UTF-8");
        System.out.println(content);

        String content1 = clientAdapter.post(entityBuilder).getContent("UTF-8");
        System.out.println(content1);
    }

    @Test
    public void wechat() {
        //创建消息参数
        TemplateMessageParam messageParam = new TemplateMessageParam(openId, templateId)
                .addItem("result", "success", "#88880000");

        //构建模板消息Api并且调用
        TemplateMessageApi.MessageResponse response =
                new WechatTemplateMessageConfig(appId, appSecret)
                        .buildApi().invoke(messageParam);

        System.out.println(response);
    }
}
