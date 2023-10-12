package com.dev.rest.config.aop;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAOP {
    private static final Logger log = LoggerFactory.getLogger(LogAOP.class);

    @Pointcut("execution(public * com.dev.rest.controller..*(..))")
    public void methodPointCut() {
    }

    @Around("methodPointCut()")
    public Object logStatics(ProceedingJoinPoint pjp) throws Throwable {
        Signature signature = pjp.getSignature();
        String className = pjp.getTarget().getClass().getName();
        String methodName = signature.getName();
        Object[] requestParams = pjp.getArgs();

        long start = System.currentTimeMillis();
        Object response = pjp.proceed();
        long end = System.currentTimeMillis();

        log.info("class:{},method:{},request:{},response:{},总耗时:{}(毫秒)", className, methodName, JSON.toJSONString(requestParams), JSON.toJSONString(response), end - start);

        return response;
    }

}
