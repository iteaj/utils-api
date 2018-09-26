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

    IErrorCode setErrMsg(String errMsg);

    /**
     * 返回错误码
     * @return
     */
    <T> T getErrCode();

    /**
     * 默认的错误码
     */
    class DefaultErrorCode implements IErrorCode {

        private String errMsg;
        private Object errCode;

        public DefaultErrorCode(String errMsg, Object errCode) {
            this.errMsg = errMsg;
            this.errCode = errCode;
        }

        @Override
        public String getErrMsg() {
            return errMsg;
        }

        @Override
        public IErrorCode setErrMsg(String errMsg) {
            this.errCode = errMsg;
            return this;
        }

        @Override
        public <T> T getErrCode() {
            return (T) errCode;
        }
    }
}
