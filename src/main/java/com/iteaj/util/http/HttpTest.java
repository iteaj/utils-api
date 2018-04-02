package com.iteaj.util.http;

import com.iteaj.util.http.adapter.JdkHttpAdapter;
import com.iteaj.util.http.build.EntityBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * create time: 2018/4/1
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class HttpTest {

    public static void main(String[] args) throws Exception {
        String url = "http://localhost:8181/frame?who=iteaj";
        File file = new File("G://idea project//ihome//karaf//deploy//README");

        jdkTest(url, file);

        clientTest(url, file);
    }

    private static void clientTest(String url, File file) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();

        HttpPost post = new HttpPost(url);
        FileBody fileBody = new FileBody(file);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addPart("upfile", fileBody);
        builder.addTextBody("heleo", "world");
        HttpEntity entity = builder.build();

//        List<NameValuePair> list = new ArrayList<>();
//        list.add(new BasicNameValuePair("heleo", "world"));
//        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list);

        post.setEntity(entity);

        CloseableHttpResponse execute = client.execute(post);
        System.out.println(execute.getEntity().getContent());
    }

    public static void jdkTest(String url, File file) {
        HttpAdapter http = new JdkHttpAdapter();
//        String s = http.get(UrlBuilder.build(url), "UTF-8");
        HttpResponse response = http.post(EntityBuilder.build(url)
                .addParam("heleo", "iteaj")
                .addBody("doing", "it")
                .addBody("hello", file));
        System.out.println(response);
    }
}
