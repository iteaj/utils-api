package com.iteaj.util.module.aop;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Create Date By 2016/10/27
 *  监控环绕通知
 * @author iteaj
 * @since 1.7
 */
public class AopExtendRoundAdvice extends RoundAdviceAbstract {

    public AopExtendRoundAdvice(Collection<ActionOutput> collection
            , Map<Method, Set<AbstractWeaveActionFactory>> methodActionMapping) {
        super(collection, methodActionMapping);
    }

    @Override
    public void beforeMethodAction(MethodInvocation methodInvocation, List<WeaveAction> actionList) {
        for (WeaveAction item : actionList){
            item.beforeMethodAction(methodInvocation.getThis(), methodInvocation.getArguments());
        }
    }

    @Override
    public void afterMethodAction(MethodInvocation methodInvocation, List<WeaveAction> actions, Object returnVal) {
        for (WeaveAction item : actions){
            item.afterMethodAction(methodInvocation.getThis(), returnVal,  methodInvocation.getArguments());
            item.generateRecord();  //生成记录
        }
    }

    @Override
    public void throwableAction(Throwable throwable, MethodInvocation methodInvocation, List<WeaveAction> actionList) {
        for (WeaveAction item : actionList){
            item.throwableAction(throwable, methodInvocation.getThis(), methodInvocation.getArguments());
        }

        afterMethodAction(methodInvocation, actionList, null);
    }

}
