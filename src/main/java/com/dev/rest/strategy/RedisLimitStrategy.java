package com.dev.rest.strategy;

import com.dev.rest.annotation.RedisLimit;
import com.dev.rest.common.utils.AddressUtils;
import com.dev.rest.enums.RedisLimitTypeEnum;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

public interface RedisLimitStrategy {

    void process(JoinPoint point, RedisLimit redisLimit);

    default String getCombineKey(RedisLimit redisLimit, JoinPoint point) {
        StringBuffer stringBuffer = new StringBuffer(redisLimit.prefix());
        stringBuffer.append(":");
        limitkeySuffix(redisLimit, point, stringBuffer);
        return stringBuffer.toString();
    }

    default void limitkeySuffix(RedisLimit redisLimit, JoinPoint point, StringBuffer stringBuffer) {
        switch (redisLimit.limitType()) {
            case IP:
                stringBuffer.append(AddressUtils.getHostIp());
                break;
            case METHOD:
                MethodSignature signature = (MethodSignature) point.getSignature();
                Method method = signature.getMethod();
                Class<?> targetClass = method.getDeclaringClass();
                stringBuffer.append(targetClass.getName()).append(":").append(method.getName());
                break;
            case URI:
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                HttpServletRequest request = attributes.getRequest();
                stringBuffer.append(request.getRequestURI());
                break;
            default:
                // DEFAULT
                break;
        }
    }
}
