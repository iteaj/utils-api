package com.iteaj.util.module;

import com.iteaj.util.Response;
import com.iteaj.util.module.mvc.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * create time: 2018/7/7
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public abstract class AbstractBaseController<T> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    public String msgForSuccess() {
        return "恭喜! 操作成功";
    }

    public String msgForFail() {
        return "抱歉! 操作失败";
    }

    /**
     * 返回当前应用的运行环境
     * @return 如果返回{@code null}则什么都不做
     */
    protected Profile getProfile() {
        return null;
    }

    /**
     * 响应失败信息到客户端 指定消息
     * @return
     */
    protected abstract Response fail(T msg);

    /**
     * 响应成功信息到客户端 指定消息
     * @return
     */
    protected abstract Response success(T msg);

    /**
     * 异常处理
     * @return
     */
    protected Response throwableHandle(Throwable e) {
        logger.error("类别：控制器 - 动作：统一异常处理 - 描述：{}", e.getMessage(), e);
        return null;
    }
}
