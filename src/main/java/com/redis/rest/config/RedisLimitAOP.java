package com.redis.rest.config;

import com.redis.rest.annotation.RedisLimit;
import com.redis.rest.enums.RedisLimitType;
import com.redis.rest.exception.RedisLimitException;
import com.redis.rest.util.IpUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author: yaodong zhang
 * @create 2023/1/3
 */
@Component
@Aspect
public class RedisLimitAOP {

    private static final Logger log = LoggerFactory.getLogger(RedisLimitAOP.class);

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 计数器限流
     * @param point
     * @param redisLimit
     */
    @Before("@annotation(redisLimit)")
    public void doBefore(JoinPoint point, RedisLimit redisLimit){
        long time = redisLimit.time();
        int count = redisLimit.count();

        String combineKey = getCombineKey(redisLimit, point);
        try {
            Long countReq = redisTemplate.opsForValue().increment(combineKey);
            if (countReq == 1) {
                // 值为1说明之前不存在该值, 因此需要设置其过期时间
                redisTemplate.expire(combineKey, time, TimeUnit.SECONDS);
            }
            if (countReq.intValue() > count) {
                throw new RedisLimitException("访问过于频繁，请稍候再试");
            }
            log.info("限制请求次数:{},当前请求次数:{},缓存key:{}", count, countReq, combineKey);
        } catch (RedisLimitException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("服务器限流异常，请稍候再试");
        }
    }

    /**
     * 动态时间窗口限流
     * @param point
     * @param redisLimit
     * @throws Throwable
     */
//    @Before("@annotation(redisLimit)")
//    public void doBefore(JoinPoint point, RedisLimit redisLimit) {
//        long time = redisLimit.time();
//        int count = redisLimit.count();
//        long currentTime = System.currentTimeMillis();
//        String combineKey = getCombineKey(redisLimit, point);
//
//        long ms = TimeUnit.MILLISECONDS.convert(time, TimeUnit.SECONDS);
//        try {
//            int number = redisTemplate.opsForZSet().rangeByScore(combineKey, currentTime - ms, currentTime).size();
//            if (number >= count) {
//                throw new RedisLimitException("访问过于频繁，请稍候再试");
//            }
//            log.info("限制请求次数:{},当前请求次数:{},缓存key:{}", count, number, combineKey);
//        } catch (RedisLimitException e) {
//            throw e;
//        } catch (Exception e) {
//            throw new RuntimeException("服务器限流异常，请稍候再试");
//        }
//
//        redisTemplate.opsForZSet().add(combineKey, UUID.randomUUID().toString(), currentTime);
//    }

    /**
     * 令牌桶限流 需要初始化令牌桶，往令牌桶中添加令牌
     *
     * redisTemplate.opsForList().rightPush(combineKey,UUID.randomUUID().toString());
     */
//    @Before("@annotation(redisLimit)")
//    public void doBefore(JoinPoint point, RedisLimit redisLimit){
//        String combineKey = getCombineKey(redisLimit, point);
//        try {
//            Object result = redisTemplate.opsForList().leftPop(combineKey);
//            if (result ==null) {
//                // 无令牌
//                throw new RedisLimitException("访问过于频繁，请稍候再试");
//            }
//        } catch (RedisLimitException e) {
//            throw e;
//        } catch (Exception e) {
//            throw new RuntimeException("服务器限流异常，请稍候再试");
//        }
//    }


    public String getCombineKey(RedisLimit redisLimit, JoinPoint point) {
        StringBuffer stringBuffer = new StringBuffer(redisLimit.key());
        stringBuffer.append(":");
        if (redisLimit.limitType() == RedisLimitType.IP) {
            stringBuffer.append(IpUtil.getIpAddr());
            stringBuffer.append(":");
        }
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Class<?> targetClass = method.getDeclaringClass();
        stringBuffer.append(targetClass.getName()).append(":").append(method.getName());
        return stringBuffer.toString();
    }

}
