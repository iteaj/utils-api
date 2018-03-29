package com.iteaj.util.module.aop.factory.time;

import com.iteaj.util.module.aop.AopExtendUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;

/**
 * Create Date By 2016/10/28
 *  常用的时间监控, 包含控制层, 业务层, dao层
 * @author iteaj
 * @since 1.7
 */
public class CommonWeaveActionFactory extends TimeWeaveActionFactory {

    private boolean isDao; //是否开启到层织入
    private boolean isBusiness; //是否开启业务层织入
    private boolean isController; //是否开启控制层织入

    /**
     * 验证此方法是否属于控制层的方法,如果是则进行监控
     * @see Controller 用此注解标注的就属于控制层
     * @see RequestMapping 用此注解标注的将被监控
     * @param method
     * @param targetClass
     * @return
     */
    @Override
    public boolean isMonitoring(Method method, Class targetClass) {

        boolean isMonitor = false;

        if(isController) {
            isMonitor = AopExtendUtils.isController(method, targetClass);
            if(isMonitor) return isMonitor;
        }

        if(isBusiness) {
            isMonitor = AopExtendUtils.isBusiness(method, targetClass);
            if(isMonitor) return isMonitor;
        }

        if(isDao){
            isMonitor =  AopExtendUtils.isDao(method, targetClass);
            if(isMonitor) return isMonitor;
        }

        return isMonitor;
    }

    public boolean isDao() {
        return isDao;
    }

    public void setDao(boolean dao) {
        isDao = dao;
    }

    public boolean isBusiness() {
        return isBusiness;
    }

    public void setBusiness(boolean business) {
        isBusiness = business;
    }

    public boolean isController() {
        return isController;
    }

    public void setController(boolean controller) {
        isController = controller;
    }

    @Override
    public String getIdentifier() {
        return "Common-Time";
    }
}

