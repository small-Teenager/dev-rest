package com.dev.rest.config.aop;

import com.dev.rest.annotation.RedisLimit;
import com.dev.rest.strategy.RedisLimitStrategy;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


/**
 * @author: yaodong zhang
 * @create 2023/1/3
 */
@Component
@Aspect
public class RedisLimitAOP {

    @Autowired
    private ApplicationContext context;

    @Before("@annotation(redisLimit)")
    public void doBefore(JoinPoint point, RedisLimit redisLimit){
        RedisLimitStrategy  limitStrategy  = context.getBean(redisLimit.limitStrategy().name(), RedisLimitStrategy.class);
        limitStrategy.process(point,redisLimit);
    }

}
