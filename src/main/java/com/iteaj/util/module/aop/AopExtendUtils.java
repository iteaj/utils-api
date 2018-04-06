package com.iteaj.util.module.aop;

import com.iteaj.util.CommonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Create Date By 2016/11/17
 *  监控工具类
 * @author iteaj
 * @since 1.7
 */
public abstract class AopExtendUtils {

    /**
     * 验证此方法是不是Object对象的
     * @param method
     * @return
     */
    public static boolean objectMethod(Method method){
        Class<?> declaring = method.getDeclaringClass();
        return Object.class == declaring;
    }

    /**
     * 是不是控制层的方法
     * @param method
     * @param targetClass
     * @return
     */
    public static boolean isController(Method method, Class targetClass){
        //监控控制层@Controller的对象
        Annotation annotation = targetClass.getAnnotation(Controller.class);
        if (null != annotation) {
            //监控包含@RequestMapping的方法
            RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
            if (null != methodAnnotation) {
                return true;
            }
        }

        return false;
    }

    /**
     * 是不是业务层的方法
     * @param method
     * @param targetClass
     * @return
     */
    public static boolean isBusiness(Method method, Class targetClass) {
        Annotation annotation = targetClass.getAnnotation(Service.class);
        if(null != annotation) {
            return !AopExtendUtils.objectMethod(method);
        }

        return false;
    }

    /**
     * 是不是Dao层的方法
     * @param method
     * @param targetClass
     * @return
     */
    public static boolean isDao(Method method, Class targetClass) {
        Class targetInterfaces = targetClass;
        //获取目标接口
        if(Proxy.isProxyClass(targetClass)){
            Class<?>[] interfaces = targetClass.getInterfaces();
            if(CommonUtils.isNotEmpty(interfaces))
                targetInterfaces = interfaces[0];

            if(targetInterfaces == null) return false;
        }

        Annotation annotation = targetInterfaces.getAnnotation(Repository.class);
        if(null != annotation) {
            return !AopExtendUtils.objectMethod(method);
        }

        return false;
    }

}
