package com.iteaj.util.module.oauth2;

import com.iteaj.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
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

    public Map<String, ContextWrapper> storage;
    private Logger logger = LoggerFactory.getLogger(getClass());

    public MapStorageManager() {
        this.storage = new ConcurrentHashMap<>();
    }

    @Override
    public long getTimeout() {
        return 30 * 1000;
    }

    @Override
    public <T extends AuthorizeContext> T getContext(String key) {
        return (T) storage.get(key);
    }

    @Override
    public void putContext(String key, AuthorizeContext context) {
        storage.put(key, new ContextWrapper(context));
    }

    @Override
    public AuthorizeContext removeContext(String key) {
        ContextWrapper contextWrapper = storage.remove(key);
        if(null != contextWrapper)
            return contextWrapper.getContext();

        return null;
    }

    /**
     * 上下文超时任务
     */
    protected class ContextTimeoutTask implements Runnable{

        private Map<String, ContextWrapper> storage = MapStorageManager.this.storage;

        @Override
        public void run() {
            if(!CommonUtils.isNotEmpty(this.storage))return;

            int count = 0;
            Iterator<ContextWrapper> iterator = storage.values().iterator();
            while (iterator.hasNext()) {
                ContextWrapper next = iterator.next();
                long currentTimeMillis = System.currentTimeMillis();
                if(currentTimeMillis - next.getCreateMillis() > getTimeout()) {
                    iterator.remove();
                    count ++;
                }
            }

            if(count > 0)
                logger.warn("类别：OAuth2 - 动作：授权上下文超时 - 描述：移除超时上下文 - 超时时长(ms)：{} - 超时数量：{}"
                        , getTimeout(), count);
        }
    }

    protected class ContextWrapper {
        private long createMillis; //创建时的豪秒数
        private AuthorizeContext context;

        public ContextWrapper(AuthorizeContext context) {
            this.context = context;
            this.createMillis = System.currentTimeMillis();
        }

        public AuthorizeContext getContext() {
            return context;
        }

        public long getCreateMillis() {
            return createMillis;
        }
    }

}
