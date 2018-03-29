package com.iteaj.util.module.upload.utils;

import java.util.Arrays;
import java.util.List;

/**
 * Create Date By 2016/8/30
 *
 * @author iteaj
 * @since 1.7
 */
public class UploadUtils {

    /**
     * 有格式的字符串转List
     * @param str   格式串
     * @param separator 分隔符
     * @return
     */
    public static List<String> stringToList(String str, String separator){
        String[] split = str.split(separator);
        return Arrays.asList(split);
    }

    /**
     * 去除最后一个路径符(eg:D://upload/  -> D://upload)
     * @param path
     * @return
     */
    public static String cutOutPath(String path){
        return path.substring(0,path.length()-1);
    }

    /**
     * 获取系统存放目录(eg:对于tomcat：${home}/temp/)
     * @return
     */
    public static String getSysUploadDir(){
        String tempDir = System.getProperty("java.io.tmpdir");
        return tempDir != null?tempDir+"/upload" : null;
    }
}
