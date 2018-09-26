package com.iteaj.util.module.mvc;

import com.iteaj.util.ApiException;
import com.iteaj.util.ApiResponse;
import com.iteaj.util.IErrorCode;
import com.iteaj.util.module.AbstractBaseController;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * create time: 2018/7/19
 *  Rest Api
 * {@code org.springframework.web.bind.annotation.RestController}
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public abstract class ApiController extends AbstractBaseController<IErrorCode> {

    static final IErrorCode WARN_ERROR_CODE = new
            IErrorCode.DefaultErrorCode("警告：请指定全局错误码", "-1");

    @Override
    protected ApiResponse fail(IErrorCode msg) {
        return new ApiResponse(msg);
    }

    @Override
    protected ApiResponse success(IErrorCode msg) {
        return new ApiResponse(msg);
    }

    @Override
    @ExceptionHandler
    protected ApiResponse throwableHandle(Throwable e) {
        logger.error("类别：Api控制器 - 动作：统一异常处理 - 描述：{}", e.getMessage(), e);
        if(e instanceof ApiException) {
            return this.fail(((ApiException) e).getCode());
        } else {
            if(getProfile() == null)
                return this.fail(getErrCode(e));

            else if(getProfile() == Profile.Prod)
                return this.fail(getErrCode(e));

            else if(getProfile() == Profile.Dev)
                return this.fail(new IErrorCode.
                        DefaultErrorCode(e.getCause().toString(), -1));

            else if(getProfile() == Profile.Test)
                return this.fail(new IErrorCode.
                        DefaultErrorCode(e.getCause().toString(), -1));

            else return this.fail(getErrCode(e));
        }
    }

    /**
     *  通过覆写此方法来自定义全局异常错误码
     * @param e
     * @return
     */
    protected IErrorCode getErrCode(Throwable e) {
        return WARN_ERROR_CODE;
    }
}
