package com.iteaj.util.module.aop.output;

import com.iteaj.util.module.aop.ActionOutput;
import com.iteaj.util.module.aop.ActionRecord;
import com.iteaj.util.module.aop.record.TimeRecord;
import com.iteaj.util.module.aop.record.ExceptionRecord;
import com.iteaj.util.module.aop.record.VoidRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.Assert;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Create Date By 2016/11/7
 *  邮件警报
 * @author iteaj
 * @since 1.7
 */
public class EmailAlarmOutput extends ActionOutput
        implements InitializingBean, BeanFactoryAware {

    private BeanFactory beanFactory;
    private boolean errorAlarm; //异常时是否发送邮件
    private boolean timeoutAlarm; //超时后是否发送邮件
    private long timeout; //超时时间(ms)


    /**是否异步发送(默认是)*/
    private boolean async;
    /**发送的邮件地址*/
    private String email;
    /**邮件标题*/
    private String title;
    /**邮件来自于*/
    private String fromMail;
    /**异步执行器*/
    private Executor executor;
    /**邮件发送器*/
    private JavaMailSender javaMailSender;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public EmailAlarmOutput() {
        this.async = true;
        this.title = "邮件警报";
    }

    @Override
    public void write(ActionRecord record) {
        //异常
        if(record instanceof ExceptionRecord && errorAlarm) {

            send(fromMail,email, title, getSendContent(record), async);
        }

        //超时
        if(record instanceof TimeRecord && timeoutAlarm
                && ((TimeRecord) record).getTime() > timeout){
            long timeout = ((TimeRecord) record).getTime() - this.timeout;
            StringBuilder sb = new StringBuilder("超时(ms)：").append(timeout).append("  具体信息如下：");
            send(fromMail,email, title, sb.toString()+getSendContent(record), async);
        }
    }

    @Override
    public boolean isMatching(ActionRecord record) {
        return !(record instanceof VoidRecord);
    }

    /**
     * 要发送的邮件内容
     * @param record 监控记录
     * @return
     */
    protected String getSendContent(ActionRecord record){
        return record.generate();
    }

    protected void send(String fromMail, String toMail, String subject,String content, boolean async) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "utf-8");
            mimeMessageHelper.setFrom(fromMail);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setTo(toMail);
            mimeMessageHelper.setText(content, true);
            if (async) {
                addSendTask(mimeMessage);
            } else {
                javaMailSender.send(mimeMessage);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加邮件发送任务
     *
     * @param mimeMessage
     *            MimeMessage
     */
    private void addSendTask(final MimeMessage mimeMessage) {
        try {
            executor.execute(new Runnable() {
                public void run() {
                    javaMailSender.send(mimeMessage);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(!isStart()) return;

        try {
            this.javaMailSender = beanFactory.getBean(JavaMailSender.class);

            Assert.hasLength(email, "未设置要发送到哪个邮件帐号");
            Assert.hasLength(fromMail, "未设置要用哪个邮件帐号进行发送");

            try {
                //如果是异步 则使用新线程进行发送
                if(isAsync()) executor = beanFactory.getBean(AsyncTaskExecutor.class);
            } catch (BeansException e) { //如果没有配置则使用单线程
                executor = Executors.newSingleThreadExecutor();
                logger.warn("类别：Aop - 动作：邮件输出 - 描述：未找到对象 {} 将创建默认线程池"+Executor.class.getName());
            }

        } catch (IllegalArgumentException e) {
            setStart(false); //手动关闭邮件输出
            logger.warn("类别：Aop - 动作：邮件输出 - 描述：{}", e.getMessage(), e);
        } catch (BeansException e) {
            setStart(false); //手动关闭邮件输出
            logger.warn("类别：Aop - 动作：邮件输出 - 描述：邮件输出对象初始化异常,将导致没办法发送邮件", e);
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public boolean isErrorAlarm() {
        return errorAlarm;
    }

    public void setErrorAlarm(boolean errorAlarm) {
        this.errorAlarm = errorAlarm;
    }

    public boolean isTimeoutAlarm() {
        return timeoutAlarm;
    }

    public void setTimeoutAlarm(boolean timeoutAlarm) {
        this.timeoutAlarm = timeoutAlarm;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public String getFromMail() {
        return fromMail;
    }

    public void setFromMail(String fromMail) {
        this.fromMail = fromMail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    public JavaMailSender getJavaMailSender() {
        return javaMailSender;
    }

    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
}
