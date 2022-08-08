package Spring_Test.Controller;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

/**
 * 构造一个拦截器
 */
@Component
public class LoginHandlerInterceptor implements HandlerInterceptor {
    Logger logger = Logger.getLogger("拦截器");
    //目标方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        Object user = request.getSession().getAttribute("loginUser");
        if(user!=null){
            //已经登录
            logger.info("拦截器显示已经是登录状态");
            return true;
        }
        //response.sendRedirect("/hello"); //若验证不通过则网页重定向
        //未经过验证
        //request.setAttribute("msg", "没权限请先登录");
        //request.getRequestDispatcher("/templates/hello.html").forward(request, response);  //请求转发函数
        logger.info("一条请求被拦截");
        request.getRequestDispatcher("/login").forward(request,response);
        return false;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
