package com.iteaj.util.module.oauth2;

import com.iteaj.util.CommonUtils;
import com.iteaj.util.Const;
import com.iteaj.util.core.UtilsGlobalDefaultFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>授权Servlet</p>
 * Create Date By 2017-03-06
 * @author iteaj
 * @since 1.7
 */
public class AuthorizeServlet extends HttpServlet {

    private AuthorizeStorageManager storageManager;
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static String INIT_PARAM_MANAGER = "OAuth2StorageManager";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String key = req.getParameter(Const.CONTEXT_PARAM_KEY);
        if(!CommonUtils.isNotBlank(key))
            throw new ServletException("OAuth2 - 未找到参数："+ Const.CONTEXT_PARAM_KEY);

        AbstractStorageContext storageContext = storageManager.getContext(key);
        try {

            if(null == storageContext)
                throw new ServletException("OAuth2 - 授权上下文释放或过期 - key：" +key);

            //重新设置HttpServletRequest和HttpServletResponse对象
            storageContext.setRequest(req);
            storageContext.setResponse(resp);

            //执行阶段链
            HashPhaseChain.instance().doPhase(storageContext);
        } catch (Exception e) {
            logger.error("类别：OAuth2授权 - 动作：回调失败 - 描述：", e.getMessage(), e);
        } finally {
            //构建执行结果
            storageContext.getAuthorizeResult().build();

            //当前执行的阶段刚好等于授权动作要调用的阶段
            AbstractAuthorizeAction authorizeAction = storageContext.getAuthorizeAction();
            if(null != authorizeAction) {
                try {
                    authorizeAction.call(storageContext.getAuthorizeResult());
                } catch (Exception e) {
                    logger.error("类别：OAuth2授权 - 动作：回调失败 - 描述：", e.getMessage(), e);
                }
            }
            storageContext.release();
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            String storageManager = getServletContext().getInitParameter(INIT_PARAM_MANAGER);
            if(CommonUtils.isNotBlank(storageManager)) {
                Class<?> forName = Class.forName(storageManager, true, getClass().getClassLoader());
                this.storageManager = (AuthorizeStorageManager)forName.newInstance();
                UtilsGlobalDefaultFactory.setDefaultStorageManager(this.storageManager);
            } else {
                this.storageManager = UtilsGlobalDefaultFactory.getDefaultStorageManager();
            }

            getServletContext().setAttribute(AuthorizeStorageManager.STORAGE_MANAGER, this.storageManager);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
