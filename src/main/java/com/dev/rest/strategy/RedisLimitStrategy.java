package com.dev.rest.strategy;

import com.dev.rest.annotation.RedisLimit;
import com.dev.rest.common.utils.AddressUtils;
import com.dev.rest.enums.RedisLimitTypeEnum;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

public interface RedisLimitStrategy {

    void process(JoinPoint point, RedisLimit redisLimit);

    default String getCombineKey(RedisLimit redisLimit, JoinPoint point) {
        StringBuffer stringBuffer = new StringBuffer(redisLimit.prefix());
        stringBuffer.append(":");
        if (redisLimit.limitType() == RedisLimitTypeEnum.IP) {
            stringBuffer.append(AddressUtils.getHostIp());
            stringBuffer.append(":");
        }
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Class<?> targetClass = method.getDeclaringClass();
        stringBuffer.append(targetClass.getName()).append(":").append(method.getName());
        return stringBuffer.toString();
    }
}
