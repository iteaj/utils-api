package com.iteaj.util.module.http.adapter;

import com.iteaj.util.CommonUtils;
import com.iteaj.util.core.UtilsException;
import com.iteaj.util.core.UtilsManagerFactory;
import com.iteaj.util.core.UtilsType;
import com.iteaj.util.module.http.*;
import com.iteaj.util.module.http.build.EntityBuilder;
import com.iteaj.util.module.http.build.TextBuilder;
import com.iteaj.util.module.http.build.UrlBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.zip.DeflaterInputStream;
import java.util.zip.GZIPInputStream;

/**
 * Create Date By 2018-03-30
 *  对Jdk自带的{@link HttpURLConnection}进行简单的封装<br>
 * @author iteaj
 * @since JDK1.7
 */
public class JdkHttpAdapter implements HttpAdapter<JdkHttpResponse> {

    /** boundary前缀 */
    private static String PREFIX = "--";
    /** 换车换行 */
    private static String ENTER = "\r\n";
    private static byte[] ENTER_BYTES = ENTER.getBytes();
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     *
     * @param builder   url构造器
     *
     * @return
     * @throws UtilsException
     */
    @Override
    public JdkHttpResponse get(UrlBuilder builder) throws UtilsException {
        if(null == builder)
            throw new UtilsException("请指定参数构建器", UtilsType.HTTP);

        HttpURLConnection connection = getConnection(builder, "GET");

        //设置http协议的请求头
        setHeaders(connection, builder);

        return doConnect(connection, builder);
    }

    /**
     *
     * @param builder 用来构建Http请求的Body内容, <br>
     *                和{@link EntityBuilder}不同的是写入body的参数只有参数Value没有参数名.
     * @return
     * @throws UtilsException
     */
    @Override
    public JdkHttpResponse post(TextBuilder builder) throws UtilsException {
        if(null == builder)
            throw new UtilsException("请指定参数构建器", UtilsType.HTTP);

        HttpURLConnection connection = getConnection(builder, "POST");

        //设置http协议的请求头
        setHeaders(connection, builder);

        /**
         * 开启和服务器的tcp连接, 并发送参数和获取返回的参数
         */
        return doConnect(connection, builder);
    }

    /**
     *
     * @param builder  用来构建Http请求的Body内容, 每一个参数都包含参数名和参数值的对应关系.
     *                 可以增加文件{@link EntityBuilder#addBody(String, File)}、
     *                 普通的参数{@link EntityBuilder#addBody(String, String)}、
     *                 流参数{@link EntityBuilder#addBody(String, String)}
     *
     * @return
     * @throws UtilsException
     */
    @Override
    public JdkHttpResponse post(EntityBuilder builder) throws UtilsException {
        if(null == builder)
            throw new UtilsException("请指定参数构建器", UtilsType.HTTP);

        HttpURLConnection connection = getConnection(builder, "POST");

        //设置http协议的请求头
        setHeaders(connection, builder);

        /**
         * 开启和服务器的tcp连接, 并发送参数和获取返回的参数
         */
        return doConnect(connection, builder);
    }

    /**
     *
     * @param builder
     * @param method
     * @return
     */
    protected HttpURLConnection getConnection(AbstractBuilder builder, String method) {
        try {
            HttpRequestConfig config = builder.getRequestConfig();

            URL url = new URL(builder.parseUrl());
            HttpURLConnection httpConnection;
            String protocol = url.getProtocol();
            if("http".equals(protocol)) {
                httpConnection = (HttpURLConnection)url.openConnection();
            } else if("https".equals(protocol)) {
                HttpsURLConnection httpsConnection = (HttpsURLConnection)url.openConnection();
                SSLContextManager sslManager = UtilsManagerFactory.getDefaultSslManager();
                httpsConnection.setSSLSocketFactory(sslManager.getSslContext().getSocketFactory());//添加ssl参数
                httpConnection = httpsConnection;
            } else {
                throw new UtilsException("不支持的协议："+protocol, UtilsType.HTTP);
            }

            httpConnection.setRequestMethod(method);
            httpConnection.setUseCaches(config.isUseCaches());
            httpConnection.setReadTimeout(config.getReadTimeout());
            httpConnection.setConnectTimeout(config.getConnectTimeout());
            httpConnection.setInstanceFollowRedirects(config.isFollowRedirects());
            return httpConnection;
        } catch (IOException e) {
            throw new UtilsException("不能获取Url连接", e, UtilsType.HTTP);
        } catch (NoSuchAlgorithmException e) {
            throw new UtilsException("Https证书算法异常", e, UtilsType.HTTP);
        } catch (KeyManagementException e) {
            throw new UtilsException("Https证书异常", e, UtilsType.HTTP);
        }
    }


    protected JdkHttpResponse doConnect(HttpURLConnection connection, AbstractBuilder builder) {
        try {
            //打开和服务器的tcp连接
            connection.connect();

            if(builder instanceof EntityBuilder) {
                //将内容写入Body
                writeEntityContent(connection, (EntityBuilder) builder);
            } else if(builder instanceof TextBuilder) {
                OutputStream outputStream = connection.getOutputStream();

                //将内容写入Body
                outputStream.write(((TextBuilder) builder).getTestString()
                        .getBytes((builder).getCharset()));
                outputStream.flush();
            } else {
                //Get request 不需要写

                /**
                 * 对于Get请求 connection.setDoOutput(false) 所以不能调用
                 * connection.getOutputStream()方法  否则抛异常
                 *
                 **/
            }

            if(connection.getResponseCode() == 200) {

                return new JdkHttpResponse(200, getResponseContent(connection), connection);
            }
            return new JdkHttpResponse(connection.getResponseCode(), null, connection);
        } catch (IOException e) {
            throw new UtilsException("发送Http请求失败", e, UtilsType.HTTP);
        } finally {
            //释放链接
            connection.disconnect();
        }
    }

    private byte[] getResponseContent(HttpURLConnection connection) throws IOException {
        InputStream inputStream = connection.getInputStream();
        String encoding = connection.getHeaderField("Content-Encoding");

        //如果没有指定内容编码则直接读取内容
        if(null == encoding) return CommonUtils.read(inputStream);

        if("gzip".equals(encoding) || "x-gzip".equals(encoding)) {
            inputStream = new GZIPInputStream(inputStream);
        } else if("deflate".equals(encoding)) {
            inputStream = new DeflaterInputStream(inputStream);
        } else if ("identity".equals(encoding)){
            /* 正常编码不需要处理 */
        } else {
            throw new UtilsException("不支持的内容编码："+ encoding, UtilsType.HTTP);
        }

        return CommonUtils.read(inputStream);
    }

    protected void writeEntityContent(HttpURLConnection connection
            , EntityBuilder builder) throws IOException {
        OutputStream output = connection.getOutputStream();

        //写入实体参数到输出流(body)
        List<EntityBuilder.EntityParam> entitys = builder.getEntitys();
        if(CommonUtils.isNotEmpty(entitys)) {
            if(ContentType.UrlEncoded == builder.getType()) {
                for(int i = 0; i< entitys.size(); i++) {
                    EntityBuilder.EntityParam item = entitys.get(i);

                    if(!CommonUtils.isNotBlank(item.getName()))
                        throw new UtilsException("构建Post输出流失败 " +
                                "原因：UrlEncoded类型 参数name必填", UtilsType.HTTP);

                    output.write(item.getName().getBytes());
                    output.write("=".getBytes());
                    output.write(item.getContent());

                    if(i!=entitys.size()-1) output.write("&".getBytes());
                }

            } else if(ContentType.Multipart == builder.getType()) {

                for (EntityBuilder.EntityParam item : entitys) {
                    output.write((PREFIX + builder.getBoundary() + ENTER).getBytes()); //必须换行
                    //写完此参数内容后必须换行
                    output.write((item.contentDisposition() + ENTER).getBytes());
                    output.write(ENTER_BYTES); //这里必须有一个空行

                    output.write(item.getContent());
                    output.write(ENTER_BYTES); //这里必须换行
                }

                // 所以的内容全部写完之后,必须在写入一行分隔符,内容与前面的有点不同：前缀+分隔符+前缀+换行,多了一个前缀
                output.write((PREFIX + builder.getBoundary() + PREFIX + ENTER).getBytes());
            } else if(ContentType.OctetStream == builder.getType()) {
                for (EntityBuilder.EntityParam item : entitys){
                    if(CommonUtils.isNotBlank(item.getName())) {
                        logger.warn("类别：Http - 动作：构建Post输出流 - 描述：OctetStream 类型带有参数名 - 处理：直接跳过");
                        continue;
                    }

                    output.write(item.getContent());
                }
            } else {
                for (EntityBuilder.EntityParam item : entitys){
                    output.write(item.getContent());
                }
            }

            output.flush();
        }
    }

    /**
     * 设置http头信息
     * @param connection
     */
    protected void setHeaders(HttpURLConnection connection, AbstractBuilder builder) {

        connection.setUseCaches(false);

        //设置成长连接
        Iterator<HttpHead> iterator = builder.iterator();
        while (iterator.hasNext()) {
            HttpHead next = iterator.next();
            connection.setRequestProperty(next.getName(), next.getValue());
        }

        if(builder instanceof EntityBuilder) {
            connection.setDoOutput(true); //设置可输出
            /**
             * 设置Content-Type类型
             * 1. multipart/form-data 声明是提交带有附件
             * 2. boundary 用来作为每个提交参数的分隔符
             */
            connection.setRequestProperty("Content-Type", builder.getType().type
                    + "; boundary=" + ((EntityBuilder) builder).getBoundary());
        } else if(builder instanceof TextBuilder) {

            connection.setDoOutput(true); //设置可输出
            connection.setRequestProperty("Content-Type", builder.getType().type);
        } else {
            connection.setRequestProperty("Content-Type", builder.getType().type);
        }
    }
}
