package com.iteaj.util.module.aop;

import com.iteaj.util.CommonUtils;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Create Date By 2016/10/27
 *  <p>环绕通知模板类</p>
 *
 *  @see Advisor
 *  @see Pointcut
 *  @see org.springframework.aop.framework.Advised
 * @author iteaj
 * @since 1.7
 */
public abstract class RoundAdviceAbstract implements MethodInterceptor, InitializingBean {

    private static List<WeaveAction> EMPTY_ACTION = new ArrayList<>();
    private static Collection<ActionOutput> _COLLECTION;

    /**
     * <p>此属性用来保存方法与动作工厂的对应(一对多)</p><br>
     *     @see AopProxyExtend#afterPropertiesSet() 在此方法里面进行实例化
     *     @see AopProxyExtend#matches(Method, Class) 在此方法里面进行初始化
     */
    private static Map<Method, Set<AbstractWeaveActionFactory>> methodActionMapping;

    public RoundAdviceAbstract() {

    }

    public RoundAdviceAbstract(Collection<ActionOutput> collection, Map<Method
            , Set<AbstractWeaveActionFactory>> methodActionMapping) {

        this._COLLECTION = collection;
        this.methodActionMapping = methodActionMapping;
    }

    @Override
    public final Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Method method = methodInvocation.getMethod();
        Object aThis = methodInvocation.getThis();
        List<WeaveAction> actions = null;
        try {
            actions = getWeaveActions(method, aThis);
            beforeMethodAction(methodInvocation, actions);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Object proceed;
        try {
            proceed = methodInvocation.proceed();
        } catch (Throwable throwable) {
            throwableAction(throwable, methodInvocation, actions);
            writeRecord(actions);
            throw throwable;
        }

        try {
            afterMethodAction(methodInvocation, actions, proceed);
            writeRecord(actions);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return proceed;
    }

    /**
     * 创建全新的动作集合{@link List< WeaveAction >}防止并发出现的线程安全问题
     * 参考{@link AbstractWeaveActionFactory}类的getMonitoringAction方法
     * 此方法都会重新实例化一个动作对象{@link WeaveAction}
     * @param method 被织入的方法
     * @param oriThis  被织入方法的宿主对象
     * @return List<WeaveAction> 如果无动作则返回{@link #EMPTY_ACTION}
     */
    protected List<WeaveAction> getWeaveActions(Method method, Object oriThis) {
        Set<AbstractWeaveActionFactory> factorySet = methodActionMapping.get(method);
        if(!CommonUtils.isNotEmpty(factorySet)) return EMPTY_ACTION;

        Iterator<AbstractWeaveActionFactory> iterator = factorySet.iterator();
        List<WeaveAction> actionList = new ArrayList<>();
        while (iterator.hasNext()){
            WeaveAction monitoringAction = iterator.next()
                    .getWeaveAction(method, oriThis.getClass());

            if(null == monitoringAction) continue;

            actionList.add(monitoringAction);
        }
        return actionList;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    public Map<Method, Set<AbstractWeaveActionFactory>> getMethodActionMapping() {
        return methodActionMapping;
    }

    public void setMethodActionMapping(Map<Method, Set<AbstractWeaveActionFactory>> methodActionMapping) {
        this.methodActionMapping = methodActionMapping;
    }

    /***
     * 方法执行之前的动作
     *  此方法会依次执行所有动作{@link WeaveAction} beforeMethodAction方法
     * @param methodInvocation
     * @param actions
     */
    protected abstract void beforeMethodAction(MethodInvocation methodInvocation, List<WeaveAction> actions);

    /***
     * 方法执行之后的动作
     * 此方法会依次执行所有动作{@link WeaveAction} afterMethodAction方法
     * @param methodInvocation
     * @param actions
     */
    protected abstract void afterMethodAction(MethodInvocation methodInvocation, List<WeaveAction> actions, Object returnVal);

    /**
     *  方法抛出异常的动作
     *  此方法会依次执行所有动作{@link WeaveAction} throwableAction方法
     * @param throwable
     * @param methodInvocation
     * @param actions
     */
    protected abstract void throwableAction(Throwable throwable, MethodInvocation methodInvocation, List<WeaveAction> actions);

    /**
     * 监控记录{@link ActionRecord}写出动作
     * 参考{@link ActionOutput}
     * @param actions 永远不会为NULL  可能为{@link #EMPTY_ACTION}
     */
    protected void writeRecord(List<WeaveAction> actions) throws Exception {
        ActionOutput next;
        ActionRecord record;
        Iterator<ActionOutput> iterator = _COLLECTION.iterator();
        while (iterator.hasNext()) {
            next = iterator.next();
            for(WeaveAction item : actions) {
                record = item.getActionRecord();

                if(null == record) continue;

                if(next.isStart() && next.isMatching(record))
                    next.write(record);
            }
        }
    }

    protected Collection<ActionOutput> getOutputs(){
        return _COLLECTION;
    }
}
