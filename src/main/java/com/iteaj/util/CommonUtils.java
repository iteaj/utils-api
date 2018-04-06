package com.iteaj.util;

import java.io.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Create Date By 2018-03-30
 *
 * @author iteaj
 * @since 1.7
 */
public abstract class CommonUtils {

    private static int EOF = -1;
    private static long GB = 1024*1024*1024;

    public static boolean isNotBlank(String param) {
        if(param == null) return false;

        return param.trim().length() > 0;
    }

    public static boolean isNotEmpty(Collection<?> params) {
        return null != params && params.size() > 0;
    }

    public static boolean isNotEmpty(Object[] params) {
        return null != params && params.length > 0;
    }

    public static Collection merge(Collection desc, Collection ori) {
        boolean descNotEmpty = isNotEmpty(desc);
        boolean oriNotEmpty = isNotEmpty(ori);
        if(!descNotEmpty)
            throw new UtilsException("合并集合的时候目标对象必须存在", UtilsType.Common);

        if(descNotEmpty && !oriNotEmpty) return desc;

        desc.addAll(ori);
        return desc;
    }

    /**
     * 获取文件名
     * e.g.   D:/iteaj/test.java -> test.java
     * @param path
     * @return
     */
    public static String fileName(String path) {
        if(!isNotBlank(path)) return "";

        String s = String.format("\\%s", File.separator);
        String[] split = path.split(s);
        return split[split.length-1];
    }

    /**
     * 返回文件后缀
     * e.g. iteaj.xml -> .xml
     * @param name
     * @return 如果不存在返回 ""
     */
    public static String suffix(String name){
        int lastIndexOf = name.lastIndexOf(".");
        if(EOF != lastIndexOf) {
            return name.substring(lastIndexOf);
        }

        return "";
    }

    public static byte[] read(File file) throws IOException {
        try(
                FileInputStream stream = new FileInputStream(file);
                ByteArrayOutputStream output = new ByteArrayOutputStream()
        ) {
            long length = file.length();
            if(length > GB) {
                copyStream(stream, output, new byte[1024 * 4]);
            } else {
                copyStream(stream, output, new byte[1024]);
            }

            return output.toByteArray();
        }

    }

    public static byte[] read(InputStream input) throws IOException {
        try(
                InputStream inputStream = input;
                ByteArrayOutputStream output = new ByteArrayOutputStream()
        ) {

            copyStream(inputStream, output, new byte[1024]);
            return output.toByteArray();
        }

    }

    /**
     * 将文件里面的数据写到一个输出流
     * @param file
     * @param output
     * @throws IOException
     */
    public static void write(File file, OutputStream output) throws IOException {
        try(
                FileInputStream input = new FileInputStream(file)
        ){
            copyStream(input, output, new byte[1024]);
        }
    }

    /**
     * 将字节数组写到一个输出流
     * @param bytes
     * @param output
     * @throws IOException
     */
    public static void write(byte[] bytes, OutputStream output) throws IOException {
        output.write(bytes);
    }

    protected static long copyStream(final InputStream inputStream
            , final OutputStream outputStream, final byte[] buffer) throws IOException {

        int t;
        long count = 0l;

        while (-1 != (t = inputStream.read(buffer))) {
            outputStream.write(buffer, 0, t);
            count += t;
        }

        return count;
    }

    public static boolean isNotEmpty(byte[] bytes) {
        return null != bytes && bytes.length > 0;
    }

    public static boolean isNotEmpty(Map fields) {
        return null != fields && fields.size() > 0;
    }

    /**
     * 合并字符串, 用separator做为分隔符
     * @param values
     * @param separator
     * @return
     */
    public static String concat(Collection<String> values, String separator) {
        if(!isNotEmpty(values)) return null;
        if(!isNotBlank(separator)) separator = "";

        StringBuilder sb = new StringBuilder();
        for(String item : values) {
            sb.append(item).append(separator);
        }
        return sb.substring(0, sb.length()-separator.length());
    }

    public static boolean isBlank(String str) {
        return null == str || str.trim().length()==0;
    }
}
