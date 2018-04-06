package com.iteaj.util.module.oauth2;

/**
 * create time: 2018/4/5
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class ContextManagerFactory {

    //默认的上下文存储管理器
    private static AuthorizeStorageManager storageManager = new MapStorageManager();

    public static AuthorizeStorageManager getDefaultManager() {
        return storageManager;
    }

    protected static void setStorageManager(AuthorizeStorageManager storageManager) {
        ContextManagerFactory.storageManager = storageManager;
    }
}
