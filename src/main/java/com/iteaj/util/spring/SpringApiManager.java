package com.iteaj.util.spring;

import com.iteaj.util.AssertUtils;
import com.iteaj.util.core.ApiReturn;
import com.iteaj.util.core.ApiParam;
import com.iteaj.util.core.UtilsApi;
import com.iteaj.util.core.UtilsType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create time: 2018/4/14
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public abstract class SpringApiManager implements InitializingBean {

    private static Map<Class<? extends ApiParam>, UtilsApi> apiMap;
    public Logger logger = LoggerFactory.getLogger(getClass());

    static {
        apiMap = new ConcurrentHashMap<>(64);
    }

    protected SpringApiManager() {

    }

    protected void registerApi(UtilsApi api) {
        AssertUtils.isTrue(null != api, "注册api失败", UtilsType.Common);

        Class apiParamType = api.getParamType();
        AssertUtils.isTrue(null != apiParamType, "未知的Api参数类型", UtilsType.Common);

        apiMap.put(apiParamType, api);
        logger.info("类别：Api工厂 - 动作：注册Api - Api：{} - 描述：{}", api.getClass().getSimpleName(), api.desc());
    }

    public static  <P extends ApiParam> UtilsApi getApi(Class<P> clazz) {
        return apiMap.get(clazz);
    }

    public static  <T extends ApiReturn> T invoke(ApiParam<T> param) {
        AssertUtils.isTrue(null != param, "未指定调用api所需参数", UtilsType.Common);
        UtilsApi api = getApi(param.getClass());

        AssertUtils.isTrue(null != api, "未找到对应的Api："
                +param.getClass().getName(), UtilsType.Common);

        return (T) api.invoke(param);
    }
}
