package com.iteaj.util.module.authorization;

import com.iteaj.util.module.authorization.http.AuthorizeServlet;

import java.util.Map;

/**
 * <P>授权类型对象</P>
 * Create Date By 2017-03-06
 * @author iteaj
 * @since 1.7
 */
public interface AuthorizationType {

    /**
     * 初始化授权类型
     */
    void init();

    /**
     * <p>返回授权类型别名</p>
     * @see TypeFactory#getTypes() 返回类型集合,每个类型的别名必须唯一
     * @return
     */
    String getTypeAlias();

    /**
     * 获取认证类型描述
     * @return
     */
    String getDescription();

    /**
     * <p>返回阶段集合的入口阶段</p><br>
     *     一个授权类型有多个授权阶段,所以必须指明哪一个阶段是执行入口
     * @return
     */
    String getPhaseEntry();

    /**
     * 授权类型的阶段流程比如：start-->code-->token
     * @return
     */
    String getProcessStage();

    /**
     * <p>指定在哪个阶段进行执行异步回调</p>
     *  一种授权类型一般在授权完成后需要异步执行某种业务功能,<br>
     *      此方法返回的参数用来指定在那个阶段执行业务动作即：{@link AsyncActionAbstract}
     *  注：当阶段链执行完这一阶段的时候将会调用注册的{@link AsyncActionAbstract},假如还有下一阶段也将不再执行
     * @return
     */
    String getAsyncPhase();

    /**
     * 返回阶段集合
     * @return
     */
    Map<String, AuthorizePhase> getPhases();

    /**
     * 返回一个授权阶段
     * @param phase
     * @return
     */
    AuthorizePhase getAuthorizePhase(String phase);

    /**
     * 注册授权阶段
     * @param phase
     */
    void registerAuthorizePhase(AuthorizePhase phase);

    /**
     * 返回{@link AuthorizeServlet} 对象的URL,
     * 必须是完整的地址：http://www.iteaj.com/grant/auth/wechat
     * @return
     */
    String getAuthorizeServletUrl();

    /**
     *  设置{@link AuthorizeServlet} 对象的URL
     */
    void setAuthorizeServletUrl(String authorizeServletUrl);
}
