package com.iteaj.util;

/**
 * create time: 2018/7/20
 *  Api 错误码
 * @see com.iteaj.util.module.mvc.ApiController
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public interface IErrorCode {

    /**
     * 返回错误消息
     * @return
     */
    String getErrMsg();

    /**
     * 返回错误码
     * @return
     */
    String getErrCode();

    /**
     * 默认的错误码
     */
    class DefaultErrorCode implements IErrorCode {

        private String errMsg;
        private String errCode;

        public DefaultErrorCode(String errMsg, String errCode) {
            this.errMsg = errMsg;
            this.errCode = errCode;
        }

        @Override
        public String getErrMsg() {
            return errMsg;
        }

        @Override
        public String getErrCode() {
            return errCode;
        }
    }
}
