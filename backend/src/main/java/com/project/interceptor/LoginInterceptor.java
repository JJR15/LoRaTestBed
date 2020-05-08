package com.project.interceptor;

import com.project.util.CONSTANT;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.project.util.CONSTANT.NGINX_PATH;

/**
 * Created by DongBaishun on 2017/7/10.
 */
@SessionAttributes({"username"})
public class LoginInterceptor implements HandlerInterceptor {

    //日志
    // protected Logger log = Logger.getLogger(getClass());
    //@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handle) throws Exception {

        HttpSession session = request.getSession();
        String user = (String) session.getAttribute("username");
        if (user == null) {
            //System.out.println("no user in LoginInterceptor!!!");
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath();
            response.sendRedirect(basePath + "/login");
            //本次访问被拦截，业务逻辑不继续执行
            return false;
        }
        //System.out.println("login with username:" + user);
        //重定向
        //返回true代表继续往下执行
        return true;
    }

    //@Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    //@Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }
}