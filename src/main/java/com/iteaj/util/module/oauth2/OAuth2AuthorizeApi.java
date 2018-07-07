package com.iteaj.util.module.oauth2;

import com.iteaj.util.CommonUtils;
import com.iteaj.util.core.ApiReturn;
import com.iteaj.util.core.UtilsException;
import com.iteaj.util.core.http.HttpApi;

import java.lang.reflect.Type;

/**
 * create time: 2018/4/5
 *  一种基于OAuth2授权的接口Api
 * @author iteaj
 * @version 1.0
 * @since JDK1.7
 */
public abstract class OAuth2AuthorizeApi<C extends OAuth2ApiParam>
        extends AbstractAuthorizeType<C> implements HttpApi<C> {

    @Override
    public InvokeReturn invoke(C param) throws UtilsException {
        super.invoke(param);
        return new InvokeReturn(param);
    }

    public class InvokeReturn extends CallAction implements ApiReturn {

        public InvokeReturn(AbstractStorageContext context) {
            super(context);
        }
    }

    @Override
    public Class<C> getParamType() {
        Type[] types = CommonUtils.getParameterizedType(this);
        if(CommonUtils.isNotEmpty(types))
            return (Class<C>) types[1];

        return null;
    }
}
