package com.jn.zfl.mySpringBoot.config;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class MyInterceptor implements HandlerInterceptor{
	Logger logger = Logger.getLogger(MyInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // TODO Auto-generated method stub
        logger.info("------preHandle------");
        //获取session
        HttpSession session = request.getSession(true);
        Map<String,String> map = (Map<String, String>) session.getAttribute("user");
        if(map == null) {
        	logger.info("------:跳转到login页面！");
        	request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            //response.sendRedirect("/");
            return false;
        }
        String username = map.get("username");
        String password = map.get("password");
        //判断用户是否存在，不存在就跳转到登录界面
        if(!"1".equals(username) && !"1".equals(password)){
            logger.info("------:跳转到login页面！");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            //response.sendRedirect("/");
            return false;
        }       
            return true;

    }
	
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
    	logger.info("postHandle");

    }
	
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub
    	logger.info("afterCompletion");

    }
}
