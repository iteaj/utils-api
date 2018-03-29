package com.iteaj.util.module.authorization.http;

import com.iteaj.util.module.authorization.PhaseChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * <p>授权Servlet</p>
 * Create Date By 2017-03-06
 * @author iteaj
 * @since 1.7
 */
public class AuthorizeServlet extends HttpServlet {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private static PhaseChain phaseChain = new HashPhaseChain();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        try {
            SessionStorageContext authorizeContext = (SessionStorageContext) session
                    .getAttribute(SessionStorageContext.AUTHORIZE_CONTEXT);

            if(null == authorizeContext)
                throw new IllegalStateException();

            //重新设置HttpServletRequest和HttpServletResponse对象
            authorizeContext.setRequest(req);
            authorizeContext.setResponse(resp);

            //执行阶段链
            phaseChain.doPhase(authorizeContext);
        } catch (IllegalStateException ie){
            logger.error("授权失败 Session丢失,请检查其配置 哈希值：[{}]",session.hashCode(), ie);
        } catch (Exception e) {
            session.removeAttribute(SessionStorageContext.AUTHORIZE_CONTEXT);
            logger.error("授权失败：", e);
        }
    }
}
