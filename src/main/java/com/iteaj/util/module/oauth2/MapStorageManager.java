package com.iteaj.util.module.oauth2;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create time: 2018/4/5
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class MapStorageManager implements AuthorizeStorageManager {

    public Map<String, AuthorizeContext> storage;

    public MapStorageManager() {
        this.storage = new ConcurrentHashMap<>();
    }

    @Override
    public <T extends AuthorizeContext> T getContext(String key) {
        return (T) storage.get(key);
    }

    @Override
    public void putContext(String key, AuthorizeContext context) {
        storage.put(key, context);
    }

    @Override
    public AuthorizeContext removeContext(String key) {
        return storage.remove(key);
    }


}
