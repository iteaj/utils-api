package com.iteaj.util.module.mvc;

import com.iteaj.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * create time: 2018/7/7
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public abstract class AbstractBaseController<R extends Response, T> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    public String msgForSuccess() {
        return "恭喜! 操作成功";
    }

    public String msgForFail() {
        return "抱歉! 操作失败";
    }

    /**
     * 响应失败信息到客户端 指定消息
     * @return
     */
    protected abstract R fail(T msg);

    /**
     * 响应成功信息到客户端 指定消息
     * @return
     */
    protected abstract R success(T msg);

    /**
     * 全局异常处理
     * @return
     */
    protected abstract R globalExceptionHandle(Throwable throwable);
}
