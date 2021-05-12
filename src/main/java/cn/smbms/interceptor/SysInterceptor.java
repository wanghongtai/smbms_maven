package cn.smbms.interceptor;

import cn.smbms.tools.Constants;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 拦截Controller，判断用户是否登录，没有登录，重定向到login.jsp
 */
public class SysInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        HttpSession session = request.getSession();
        Object user = session.getAttribute(Constants.USER_SESSION);
        if(user == null){
            //重定向到登录页面
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath+"/login.jsp");
            return false;
        }
        return true; //放行拦截
    }


    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
