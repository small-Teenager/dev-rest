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
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author: yaodong zhang
 * @create 2023/1/3
 */
@Component
@Aspect
public class RedisLimitAOP {

    private static final Logger log = LoggerFactory.getLogger(RedisLimitAOP.class);
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    //简单限流
    @Before("@annotation(redisLimit)")
    public void doBefore(JoinPoint point, RedisLimit redisLimit){
        long time = redisLimit.time();
        int count = redisLimit.count();

        String combineKey = getCombineKey(redisLimit, point);
        try {
            Long countReq = null;
            if (stringRedisTemplate.hasKey(combineKey)) {
                countReq = stringRedisTemplate.opsForValue().increment(combineKey);
            } else {
                countReq = stringRedisTemplate.opsForValue().increment(combineKey);
                stringRedisTemplate.expire(combineKey, time, TimeUnit.SECONDS);
            }

            if (countReq == null || countReq.intValue() > count) {
                throw new RedisLimitException("访问过于频繁，请稍候再试");
            }
            log.info("限制请求次数:{},当前请求次数:{},缓存key:{}", count, countReq, combineKey);
        } catch (RedisLimitException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("服务器限流异常，请稍候再试");
        }
    }

    //动态时间窗口限流
//    @Before("@annotation(redisLimit)")
//    public void doBefore(JoinPoint point, RedisLimit redisLimit) throws Throwable {
//            String key = redisLimit.key();
//            long time = redisLimit.time();
//            int count = redisLimit.count();
//
//            String combineKey = getCombineKey(redisLimit, point);
//            List<Object> keys = Collections.singletonList(combineKey);
//            long now = System.currentTimeMillis();
//            final long ms = TimeUnit.MILLISECONDS.convert(time, TimeUnit.SECONDS);
//            try {
//                Boolean number = redisTemplate.execute(RedisLimitScript1, keys, count, now, ms);
//                if (!number) {
//                    throw new RedisLimitException("访问过于频繁，请稍候再试");
//                }
//                log.info("限制请求'{}',缓存key'{}'", count, key);
//            } catch (RedisLimitException e) {
//                throw e;
//            } catch (Exception e) {
//                throw new RuntimeException("服务器限流异常，请稍候再试");
//            }
//    }

//    //令牌桶限流
//    @Before("@annotation(rateRedisLimiter)")
//    public void doBefore(JoinPoint point, RedisLimit rateRedisLimiter) throws Throwable {
//        String key = rateRedisLimiter.key();
//        int time = rateRedisLimiter.time();
//        int count = rateRedisLimiter.count();
//        String combineKey = getCombineKey(rateRedisLimiter, point);
//        List<Object> keys = Collections.singletonList(combineKey);
//        long now = System.currentTimeMillis();
//        final long ms = TimeUnit.MILLISECONDS.convert(time, TimeUnit.SECONDS);
//        try {
//            Long number = redisTemplate.execute(RedisLimitScript2, keys, time, count);
//            if (number < 0) {
//                throw new RedisLimitException("访问过于频繁，请稍候再试");
//            }
//            log.info("限制请求'{}',当前令牌数'{}',缓存key'{}'", count, number, key);
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

    private static final String UNKNOWN = "unknown";

    public String getIpAddress() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
