package com.iteaj.util.http.build;

import com.iteaj.util.CommonUtils;
import com.iteaj.util.UtilsException;
import com.iteaj.util.UtilsType;
import com.iteaj.util.http.AbstractBuilder;
import com.iteaj.util.http.ContentType;
import com.iteaj.util.http.adapter.JdkHttpAdapter;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * create time: 2018/3/31
 *  实体构建器, 用来构建Post请求需要的参数
 * @see #entitys 此集合里面的数据会全部写到 http body里面
 * @see #boundary 当Head的Content-Type为{@link ContentType#Multipart}时,<br>
 *     作为每个参数的分隔符具体看{@link JdkHttpAdapter#writeEntityContent(HttpURLConnection, EntityBuilder)}
 * @author iteaj
 * @version 1.0
 * @since 1.7
 */
public class EntityBuilder extends AbstractBuilder {

    private String boundary; //body字段与字段之间的分隔符
    private List<EntityParam> entitys;
    private static byte[] EMPTY_CONTENT = new byte[0];

    private EntityBuilder(String url) {
        this(url, ContentType.Multipart);
    }

    private EntityBuilder(String url, ContentType type) {
        super(url, type);
        this.entitys = new ArrayList<>();
        this.boundary = UUID.randomUUID().toString();
    }

    /**
     * 构建一个实例对象
     * contentType 默认为 {@link ContentType#UrlEncoded}
     * @param url   必填
     * @return
     */
    public static EntityBuilder build(String url) throws UtilsException {
        return build(url, ContentType.UrlEncoded);
    }

    /**
     *
     * @param url
     * @param contentType
     * @return
     */
    public static EntityBuilder build(String url, ContentType contentType) {
        if(!CommonUtils.isNotBlank(url))
            throw new UtilsException("http Url错误", UtilsType.HTTP);

        return new EntityBuilder(url, contentType);
    }

    @Override
    public EntityBuilder addParam(String name, String value) {
        super.addParam(name, value);
        return this;
    }

    /**
     * 往Body里面写入String -> String的参数
     * 注意：默认编码为：UTF-8
     * @see ContentType#Plain 此参数的Content-Type类型
     * @param name
     * @param value
     * @return
     */
    public EntityBuilder addBody(String name, String value) {
        return this.addBody(name, value, "UTF-8");
    }

    /**
     * 往Body里面写入String -> String的参数, 并可以指定写入的编码
     * @see ContentType#Plain 此参数的Content-Type类型
     * @param name
     * @param value
     * @param charset
     * @return
     */
    public EntityBuilder addBody(String name, String value, String charset) {
        entitys.add(new EntityParam(name, value, charset));
        return this;
    }

    /**
     * 往Body里面写入 String,byte[]的参数
     * @see ContentType#OctetStream 此参数的Content-Type类型
     * @param name
     * @param bytes
     * @return
     */
    public EntityBuilder addBody(String name, byte[] bytes) {
        entitys.add(new EntityParam(name, bytes));
        return this;
    }

    /**
     * 往Body里面写入 String,File的参数
     * @see ContentType#OctetStream 此参数的Content-Type类型
     * @param name
     * @param file
     * @return
     */
    public EntityBuilder addBody(String name, File file) {
        try {
            if(!CommonUtils.isNotBlank(name) || null == file)
                throw new UtilsException("增加http实体的所需参数错误", UtilsType.HTTP);

            byte[] read = CommonUtils.read(file);

            //获取文件名
            String fileName = CommonUtils.fileName(file.getPath());
            EntityParam entityParam = new EntityParam(name, fileName
                    , read, ContentType.OctetStream);

            //如果上传文件则手动将Context-Type设置为Multipart
            this.type = ContentType.Multipart;
            entitys.add(entityParam);
        } catch (IOException e) {
            throw new UtilsException("读取文件失败", e, UtilsType.HTTP);
        }

        return this;
    }

    public String getBoundary() {
        return boundary;
    }

    public List<EntityParam> getEntitys() {
        return entitys;
    }

    /**
     * @see #name 参数名称
     * @see #value 表单提交的表单名称, 即文件名
     * @see #type  Content-Type
     * @see #content 参数内容
     */
    public class EntityParam extends UrlParam {

        private byte[] content;
        private boolean isFile;
        private ContentType type;

        public EntityParam(String name, String value) {
            super(name, value, "UTF-8");
        }

        public EntityParam(String name, String value, String charset) {
            super(name, value, charset);
        }

        public EntityParam(String name, byte[] bytes) {
            super(name, null);
            this.content = bytes;
        }

        public EntityParam(String name, String value, byte[] content, ContentType type) {
            super(name, value);
            this.type = type;
            this.isFile = true;
            this.content = content;
        }

        public byte[] getContent() throws UnsupportedEncodingException {
            //将值转成字节数组
            if(!CommonUtils.isNotEmpty(content)
                    && CommonUtils.isNotBlank(getValue()))
                content = getValue().getBytes(getCharset());

            else content = EMPTY_CONTENT;

            return content;
        }

        public EntityParam setContent(byte[] content) {
            this.content = content;
            return this;
        }

        public ContentType getType() {
            return type;
        }

        public EntityParam setType(ContentType type) {
            this.type = type;
            return this;
        }

        public boolean isFile() {
            return isFile;
        }

        public void setFile(boolean file) {
            isFile = file;
        }

        public String contentDisposition() {

            StringBuilder sb = new StringBuilder();
            //在这里getValue()返回的是文件名
            //即如果文件名存在, 则相当于上传文件 设置文件名
            sb.append("Content-Disposition: form-data; name=\"").append(getName()).append('"');
            if(isFile) {
                sb.append("; filename=\"").append(getValue()).append("\"\r\n")
                        .append("Content-Type: octet-stream").append("\r\n")
                        .append("Content-Transfer-Encoding: binary\r\n");
            } else if(ContentType.OctetStream == getType()) {
                sb.append("\r\n").append("Content-Type: octet-stream").append("\r\n")
                        .append("Content-Transfer-Encoding: binary");
            } else {
                sb.append("\r\n").append("Content-Type: text/plain; ").append(getCharset())
                        .append("\r\n") .append("Content-Transfer-Encoding: 8bit");
            }

            return sb.toString();
        }
    }
}
