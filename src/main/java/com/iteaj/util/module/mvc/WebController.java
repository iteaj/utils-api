package com.iteaj.util.module.mvc;

import com.iteaj.util.WebResponse;
import com.iteaj.util.module.AbstractBaseController;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * create time: 2018/7/20
 *  普通的Web项目使用的Api
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public abstract class WebController extends
        AbstractBaseController<String> {

    protected WebResponse fail() {
        return this.fail(msgForFail());
    }

    @Override
    protected WebResponse fail(String msg) {
        return new WebResponse(false, msg);
    }

    protected WebResponse success() {
        return this.success(msgForSuccess());
    }

    @Override
    protected WebResponse success(String msg) {
        return new WebResponse(true, msg);
    }

    @Override
    @ExceptionHandler
    protected WebResponse throwableHandle(Throwable e) {
        logger.error("类别：MVC控制器 - 动作：统一异常处理 - 描述：{}", e.getMessage(), e);

        //如果是调试说明处于开发状态, 返回异常信息
        if(getProfile() == null)
            return this.fail();
        else if(getProfile() == Profile.Prod)
            return this.fail();
        else if(getProfile() == Profile.Dev)
            return this.fail(e.getMessage());
        else if(getProfile() == Profile.Test)
            return this.fail(e.getMessage());
        else return this.fail();
    }
}
