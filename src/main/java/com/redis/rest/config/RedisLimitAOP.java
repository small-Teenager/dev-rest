//package com.redis.rest.config;
//
//import com.redis.rest.annotation.RedisLimit;
//import com.redis.rest.response.ApiResponse;
//import org.apache.commons.lang3.StringUtils;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.stereotype.Component;
//
//import javax.security.auth.login.LoginException;
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author: yaodong zhang
// * @create 2023/1/3
// */
//@Component
//@Aspect
//public class RedisLimitAOP {
//    private static final Logger log = LoggerFactory.getLogger(RedisLimitAOP.class);
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//
//    @Around("@annotation(com.redis.rest.annotation.RedisLimit)")
//    public Object handleLimit(ProceedingJoinPoint joinPoint) {
//        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//        Method method = methodSignature.getMethod();
//        RedisLimit redisLimitAnno = method.getAnnotation(RedisLimit.class);
//
//        String identifier = redisLimitAnno.identifier();
//        long watch = redisLimitAnno.watch();
//        int times = redisLimitAnno.times();
//        long lock = redisLimitAnno.lock();
//
//
//        String identifierValue = null;
//        try {
//            Object arg = joinPoint.getArgs()[0];
//            Field declaredField = arg.getClass().getDeclaredField(identifier);
//            declaredField.setAccessible(true);
//            identifierValue = (String) declaredField.get(arg);
//        } catch (NoSuchFieldException e) {
//            log.error(">>> invalid identifier [{}], cannot find this field in request params", identifier);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        if (StringUtils.isBlank(identifierValue)) {
//            log.error(">>> the value of RedisLimit.identifier cannot be blank, invalid identifier: {}", identifier);
//        }
//
//        // check User locked
//        ValueOperations<String, String> ssOps = stringRedisTemplate.opsForValue();
//        String flag = ssOps.get(identifierValue);
//        if (flag != null && "lock".contentEquals(flag)) {
//            return ApiResponse.error(404, "user locked");
//        }
//
//        ApiResponse result;
//        try {
//            result = (ApiResponse) joinPoint.proceed();
//        } catch (Throwable e) {
//            result = handleLoginException(e, identifierValue, watch, times, lock);
//        }
//        return result;
//    }
//
//    private ApiResponse handleLoginException(Throwable e, String identifierValue, long watch, int times, long lock) {
//        ApiResponse result = new ApiResponse();
//        result.setCode(404);
//        if (e instanceof LoginException) {
//            log.info(">>> handle login exception...");
//            final ValueOperations<String, String> ssOps = stringRedisTemplate.opsForValue();
//            Boolean exist = stringRedisTemplate.hasKey(identifierValue);
//            // key doesn't exist, so it is the first login failure
//            if (exist == null || !exist) {
//                ssOps.set(identifierValue, "1", watch, TimeUnit.SECONDS);
//                return ApiResponse.success(result);
//            }
//
//            String count = ssOps.get(identifierValue);
//            // has been reached the limitation
//            if (Integer.parseInt(count) + 1 == times) {
//                log.info(">>> [{}] has been reached the limitation and will be locked for {}s", identifierValue, lock);
//                ssOps.set(identifierValue, "lock", lock, TimeUnit.SECONDS);
//                result.setMsg("user locked");
//                return ApiResponse.success(result);
//            }
//            ssOps.increment(identifierValue);
//            result.setMsg(e.getMessage() + "; you have try " + ssOps.get(identifierValue) + "times.");
//        }
//        log.error(">>> RedisLimitAOP cannot handle {}", e.getClass().getName());
//        return ApiResponse.success(result);
//    }
//}
