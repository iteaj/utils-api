package com.iteaj.util.module.aop;

import com.iteaj.util.module.aop.factory.ExceptionWeaveActionFactory;
import com.iteaj.util.module.aop.record.ExceptionRecord;

import java.util.Date;

/**
 * Create Date By 2016/10/28
 *  <p>织入记录</p><br>
 *  @see WeaveAction#generateRecord() 每一个织入动作都会生成一条动作记录{@link ActionRecord}
 *  @see AbstractWeaveActionFactory#isMatchingRecord(Class)  一个织入动作可以对应多种动作记录类型<br>
 *      比如：{@link ExceptionWeaveActionFactory}
 *      对应的记录类型{@link ExceptionRecord}
 *  @see Cloneable 此记录必须可以被克隆
 *  @see AopProxyExtend#afterPropertiesSet()
 * @author iteaj
 * @since 1.7
 */
public interface ActionRecord extends Cloneable {

    /**
     * 记录标识
     * @return
     */
    String getId();

    /**
     * 返回字符串形式的记录信息
     * @return
     */
    String generate();

    /**
     * 获取记录类型
     * @return
     */
    String getDesc();

    /**
     * 获取记录生成时间
     * @return
     */
    Date getDate();

    /**
     * 获取此记录所属动作
     * @return
     */
    WeaveAction getAction();

}
