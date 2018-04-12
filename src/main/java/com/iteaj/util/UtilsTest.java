package com.iteaj.util;

import com.iteaj.util.core.task.TimeoutTask;
import com.iteaj.util.core.task.TimeoutTaskManager;
import com.iteaj.util.module.http.adapter.HttpClientAdapter;
import com.iteaj.util.module.http.adapter.HttpClientResponse;
import com.iteaj.util.module.http.adapter.JdkHttpAdapter;
import com.iteaj.util.module.http.adapter.JdkHttpResponse;
import com.iteaj.util.module.http.build.EntityBuilder;
import com.iteaj.util.module.http.build.TextBuilder;
import com.iteaj.util.module.http.build.UrlBuilder;
import com.iteaj.util.module.wechat.message.TemplateMessageApi;
import com.iteaj.util.module.wechat.message.TemplateMessageParam;
import com.iteaj.util.module.wechat.message.WechatTemplateMessageConfig;
import org.junit.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

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

    @Test
    public void timerTask() throws InterruptedException {
        final TimeoutTaskManager instance = TimeoutTaskManager.instance();

        for(int i=0; i< 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Random random = new Random();
                        final int i1 = random.nextInt(10000);
                        for(int t = 1; t<3; t++) {
                            Thread.sleep(random.nextInt(3000));
                            instance.addTask(new TimeoutTask(i1, TimeUnit.MILLISECONDS) {
                                @Override
                                public void run() {
                                    long currentTimeMillis = System.currentTimeMillis();
                                    long test = currentTimeMillis - this.getCreateTime();
                                    System.out.println("超时：" + i1 + " 流逝：" + test + "偏差：" + (i1 - test));
                                }
                            }.build());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        Thread.sleep(60000);
    }
}
