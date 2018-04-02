package com.iteaj.util.http;

import com.iteaj.util.UtilsException;
import com.iteaj.util.http.adapter.HttpClientAdapter;
import com.iteaj.util.http.adapter.JdkHttpAdapter;
import com.iteaj.util.http.build.EntityBuilder;
import com.iteaj.util.http.build.SimpleBuilder;
import com.iteaj.util.http.build.UrlBuilder;

import java.io.File;

/**
 * create time: 2018/3/27
 *  Http适配器, 用于适配HttpClient{@link HttpClientAdapter} 、<br>
 *      jdk自带{@link java.net.HttpURLConnection} {@link JdkHttpAdapter}等.
 *      向外提供一套简单易用的统一的Api
 * @author iteaj
 * @version 1.0
 * @since 1.7
 */
public interface HttpAdapter<T extends HttpResponse> {

    /**
     *  发送Get请求
     * @param builder   url构造器
     *
     * @return 返回的数据进行utf-8编码
     * @throws UtilsException
     */
    T get(UrlBuilder builder) throws UtilsException;

    /**
     * 简单的实体构建器
     * @param builder 用来构建Http请求的Body内容, <br>
     *                和{@link EntityBuilder}不同的是写入body的参数只有参数Value没有参数名.
     *                而且不能以链式的操作不断增加内容, 只能写一次,
     *                如果第二次调用addBody增数据将覆盖第一次的内容
     * @see SimpleBuilder#body
     * @return
     * @throws UtilsException
     */
    T post(SimpleBuilder builder) throws UtilsException;

    /**
     * 发送Post请求
     * @param builder  用来构建Http请求的Body内容, 每一个参数都包含参数名和参数值的对应关系.
     *                 可以增加文件{@link EntityBuilder#addBody(String, File)}、
     *                 普通的参数{@link EntityBuilder#addBody(String, String)}、
     *                 流参数{@link EntityBuilder#addBody(String, String)}
     *
     * @return
     * @throws UtilsException
     */
    T post(EntityBuilder builder) throws UtilsException;

}
