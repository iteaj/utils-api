package com.iteaj.util.module.oauth2;

import com.iteaj.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>spring支持的授权类型工厂</p>
 * Create Date By 2017-03-07
 * @author iteaj
 * @since 1.7
 */
public class SpringAuthorizeTypeFactory implements TypeFactory,InitializingBean,BeanFactoryAware {

    private BeanFactory beanFactory;
    private Map<String, AuthorizationType> typeMap;
    private List<AuthorizationType> authorizationTypes;
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public <T extends AuthorizationType> T getType(Class<T> clazz) {
        return beanFactory.getBean(clazz);
    }

    @Override
    public <T extends AuthorizationType> T getType(String keyAlias, Class<T> clazz) {
        return (T)typeMap.get(keyAlias);
    }

    @Override
    public Map<String, AuthorizationType> getTypes() {
        return typeMap;
    }

    @Override
    public void registerAuthorizationType(AuthorizationType type) {
        if(type == null || !CommonUtils.isNotBlank(type.getTypeAlias()))
            throw new IllegalArgumentException("授权类型获取类型别名不能为空");

        if(typeMap.containsKey(type.getTypeAlias()))
            return;

        typeMap.put(type.getTypeAlias(), type);
    }

    public void setAuthorizationTypes(List<AuthorizationType> authorizationTypes) {
        this.authorizationTypes = authorizationTypes;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(CollectionUtils.isEmpty(authorizationTypes))
            return;

        if(CollectionUtils.isEmpty(typeMap)){
            typeMap = new HashMap<>();
        } else {
            return;
        }

        for(AuthorizationType item : authorizationTypes){
            registerAuthorizationType(item);
        }
    }

    private String getProcessStage(AuthorizationType type){
        if(!CommonUtils.isNotBlank(type.getProcessStage()))
            return "《警告》没有指定授权阶段流程";

        return type.getProcessStage();
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
