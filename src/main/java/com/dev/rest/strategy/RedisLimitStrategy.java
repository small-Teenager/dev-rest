package com.dev.rest.strategy;

import com.dev.rest.annotation.RedisLimit;
import com.dev.rest.common.utils.AddressUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

public interface RedisLimitStrategy {

    void process(JoinPoint point, RedisLimit redisLimit);

    default String getCombineKey(RedisLimit redisLimit, JoinPoint point) {
        StringBuilder sb = new StringBuilder(redisLimit.prefix());
        sb.append(":");
        limitkeySuffix(redisLimit, point, sb);
        return sb.toString();
    }

    default void limitkeySuffix(RedisLimit redisLimit, JoinPoint point, StringBuilder sb) {
        switch (redisLimit.limitType()) {
            case IP:
                sb.append(AddressUtils.getHostIp());
                break;
            case METHOD:
                MethodSignature signature = (MethodSignature) point.getSignature();
                Method method = signature.getMethod();
                Class<?> targetClass = method.getDeclaringClass();
                sb.append(targetClass.getName()).append(":").append(method.getName());
                break;
            case URI:
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                HttpServletRequest request = attributes.getRequest();
                sb.append(request.getRequestURI());
                break;
            default:
                // DEFAULT
                break;
        }
    }
}
