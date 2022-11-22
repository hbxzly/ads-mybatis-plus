package com.hbx.ads.log;


import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
public class SysLog {

    private Logger log = Logger.getLogger(SysLog.class);

    /**
     * 配置切入点
     */
    @Pointcut("execution(* com.hbx.ads.controller..*.*(..))")
    public void controllerAspect() {

    }

    @After("controllerAspect()")
    public void after(JoinPoint joinPoint) {
        System.out.println("进来切点了");
    }
}
