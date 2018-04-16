package com.iteaj.util.spring;

import com.iteaj.util.AssertUtils;
import com.iteaj.util.core.ApiInvokeReturn;
import com.iteaj.util.core.ApiParam;
import com.iteaj.util.core.UtilsApi;
import com.iteaj.util.core.UtilsType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * create time: 2018/4/14
 *
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public abstract class AbstractApiManager implements SpringApiManager {

    private Map<Class<? extends ApiParam>, UtilsApi> apiMap;
    public Logger logger = LoggerFactory.getLogger(getClass());

    public AbstractApiManager() {
        apiMap = new HashMap<>();
    }

    public void registerApi(UtilsApi api) {
        AssertUtils.isTrue(null != api, "注册api失败", UtilsType.Common);
        apiMap.put(api.getParamType(), api);
        logger.info("类别：Api工厂 - 动作：注册Api - 描述：{} - Api：{}", api.desc(), api.getClass().getSimpleName());
    }

    @Override
    public <P extends ApiParam> UtilsApi getApi(Class<P> clazz) {
        return apiMap.get(clazz);
    }

    @Override
    public <T extends ApiInvokeReturn> T invoke(ApiParam<T> param) {
        AssertUtils.isTrue(null != param, "未指定调用api所需参数", UtilsType.Common);
        UtilsApi api = getApi(param.getClass());

        AssertUtils.isTrue(null != api, "未找到对应的Api："
                +param.getClass().getName(), UtilsType.Common);

        return (T) api.invoke(param);
    }
}
