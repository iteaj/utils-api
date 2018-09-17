package com.iteaj.util.module.mvc;

import com.iteaj.util.JsonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * create time: 2018/7/20
 *  普通的Web项目使用的Api
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public abstract class WebController extends AbstractBaseController<JsonResponse, String> {

    protected JsonResponse fail() {
        return this.fail(msgForFail());
    }

    @Override
    protected JsonResponse fail(String msg) {
        return new JsonResponse(false, msg);
    }

    protected JsonResponse success() {
        return this.success(msgForSuccess());
    }

    @Override
    protected JsonResponse success(String msg) {
        return new JsonResponse(true, msg);
    }

    @Override
    @ResponseBody
    @ExceptionHandler
    protected JsonResponse globalExceptionHandle(Throwable e) {
        logger.error("类别：控制器异常 - 动作：全局异常处理 - 描述：{}", e.getMessage(), e);
        //如果是调试说明处于开发状态, 返回异常信息
        if(logger.isDebugEnabled())
            return this.fail(e.getMessage());
        else // 开发环境返回文明语
            return this.fail();
    }
}
