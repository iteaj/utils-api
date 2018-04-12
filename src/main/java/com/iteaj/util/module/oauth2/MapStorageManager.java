package com.iteaj.util.module.oauth2;

import com.iteaj.util.core.task.TimeoutTask;
import com.iteaj.util.core.task.TimeoutTaskManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * create time: 2018/4/5
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public class MapStorageManager implements AuthorizeStorageManager {

    public Map<String, AuthorizeContext> storage;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public MapStorageManager() {
        this.storage = new ConcurrentHashMap<>();
    }

    @Override
    public int getTimeout() {
        return 30;
    }

    @Override
    public <T extends AuthorizeContext> T getContext(String key) {
        AuthorizeContext authorizeContext = storage.get(key);
        if(null == authorizeContext) return null;

        return (T) authorizeContext;
    }

    @Override
    public void putContext(final String key, AuthorizeContext context) {
        storage.put(key, context);
        TimeoutTaskManager.instance().addTask(new TimeoutTask(getTimeout()
                , TimeUnit.SECONDS) {
            @Override
            public void run() {
                AuthorizeContext remove = storage.remove(key);
                if(logger.isDebugEnabled()) {
                    logger.debug("类别：OAuth2授权 - 动作：移除超时上下文 - 超时(s)：{} - 状态：{}"
                            , getTimeout(), remove != null?"超时移除":"正常释放");
                }
            }
        }.build());
    }

    @Override
    public AuthorizeContext removeContext(String key) {
        AuthorizeContext context = storage.remove(key);
        if(null != context)
            return context;

        return null;
    }

}
