package com.iteaj.util.module.aop.output;

import com.iteaj.util.module.aop.ActionOutput;
import com.iteaj.util.module.aop.ActionRecord;
import com.iteaj.util.module.aop.record.ExceptionRecord;
import com.iteaj.util.module.aop.record.VoidRecord;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * Create Date By 2016/10/28
 *  slf4j日志输出
 * @author iteaj
 * @since 1.7
 */
public class Slf4JActionOutput extends ActionOutput implements InitializingBean {

    public Logger logger;
    private String loggerName;

    @Override
    public void write(ActionRecord record) {
        if(record instanceof ExceptionRecord){
            Throwable throwable = ((ExceptionRecord) record).getThrowable();
            logger.warn("AopExt - {}", record.generate(), throwable);

        } else {

            if (logger.isDebugEnabled()) {
                logger.debug("AopExt - {}", record.generate(), record.getDate());
            } else if (logger.isInfoEnabled()) {
                logger.info("AopExt - {}", record.generate(), record.getDate());
            } else if (logger.isWarnEnabled()) {
                logger.warn("AopEx - {}", record.generate(), record.getDate());
            }
        }
    }

    @Override
    public boolean isMatching(ActionRecord record) {
        return record instanceof ActionRecord
                && !(record instanceof VoidRecord);
    }

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(StringUtils.isBlank(this.loggerName))
            this.loggerName = "Slf4jMonitoring";

        this.logger = LoggerFactory.getLogger(getLoggerName());
    }

    @Override
    public void setStart(boolean start) {
        super.setStart(start);
    }
}
