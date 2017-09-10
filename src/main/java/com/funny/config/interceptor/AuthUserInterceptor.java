package com.funny.config.interceptor;

import com.funny.config.annotation.AuthCheckUser;
import com.funny.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by liuxin on 2017/6/27.
 * 用户拦截器校验用户是否登录
 */
@Component
public class AuthUserInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AuthUserInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        logger.info("===========================拦截器启动=============================");
        HandlerMethod methodHandler = (HandlerMethod) handler;
        request.setAttribute("starttime", System.currentTimeMillis());
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
//        response.setHeader("Access-Control-Allow-Headers", "Cache-Control, Pragma, Origin, Authorization, Content-Type, X-Requested-With");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
        //从方法中获取该注解，如果注解不存在或者注解的不开启校验行为，则直接通过
        AuthCheckUser auth = methodHandler.getMethodAnnotation(AuthCheckUser.class);
        if (auth == null) {
            return true;
        }
        if (auth.check()) {
            //TODO 校验用户信息

        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
        logger.info("===========================执行处理完毕=============================");
        long starttime = (long) request.getAttribute("starttime");
        request.removeAttribute("starttime");
        long endtime = System.currentTimeMillis();
        logger.info("============请求地址：" + request.getRequestURI() + "：处理时间：{}", (endtime - starttime) + "ms");

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        logger.info("============================拦截器关闭==============================");
    }
}
