package com.funny.config.aop;

import com.funny.util.ResponseBuilder;
import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * @Package: foxlife.base
 * @Description: 拦截请求，打印出请求信息
 * @author: liuxin
 * @date: 17/2/20 下午7:33
 */
@Aspect
@Component
public class WebLogAspect {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final Gson gson = new Gson();

    /**
     * rest包和子包里面的所有方法
     */
    @Pointcut("execution(public * com.funny.rest.*.*(..))")
    public void weblog() {
    }

    @Before("weblog()")
    public void doBefore(JoinPoint joinpoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        log.info("URL : " + request.getRequestURL().toString());
        log.info("请求类型 : " + request.getMethod());
        log.info("请求IP : " + getRemoteHost(request));
        log.info("方法 : " + joinpoint.getSignature().getDeclaringTypeName() + "." + joinpoint.getSignature().getName());
        log.info("参数列表 : " + joinpoint.getArgs());
    }

    @AfterReturning(returning = "ret", pointcut = "weblog()")
    public void doAfterReturning(ResponseBuilder.IResponseVo ret) throws Throwable {
        // 处理完请求，返回内容
        log.info("返回Code状态 : " +gson.toJson(ret));
    }

    /**
     * 获取正式ip地址
     * @param request
     * @return
     */
    public String getRemoteHost(javax.servlet.http.HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
    }

}

