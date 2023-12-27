package com.dev.rest.config.aop;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.dev.rest.annotation.SignatureValidation;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author zhangyaodong
 * @version 1.0
 * @description 开放接口的数据安全及身份识别，需要编码方式一致
 * @create 2023/12/20 19:49
 */
@Component
@Aspect
public class SignatureVerificationAop {

    private static final Logger log = LoggerFactory.getLogger(SignatureVerificationAop.class);

    /**
     * 1.signPayLoad = appId + appKey + timestamp + nonce + reqData
     * 2.reqData = post 请求 body 携带的json 数据(注意请求顺序)
     * 3.signature = （ appSecret + signPayLoad ）加密
     * timestamp 毫秒级时间戳
     * nonce 随机数 与timestamp 有关 防止重放
     * appId 应用的唯一标识(审核通过后下发) 同一个appId 下可以存在多个账号
     * appKey 公钥(相当于账号)(需要验证账号是否可用)
     * appSecret 私钥(相当于密码) 需要防止泄漏
     *
     * @param joinPoint
     * @param signatureValidation
     * @return
     * @throws IllegalAccessException
     */
    @Before("@annotation(signatureValidation)")
    public void validateSignature(JoinPoint joinPoint, SignatureValidation signatureValidation) throws IllegalAccessException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        // 1.根据timestamp 校验是否超时
        // 2.校验appId + appKey 合法性
        // 3.根据appId + appKey 获取appSecret
        // 4.生成签名，与请求头中的signature 对比
        // 5.根据nonce 防止重放
        String timestampStr = request.getHeader("timestamp");// 毫秒值
        Long timestamp = Long.parseLong(timestampStr);
        long now = System.currentTimeMillis();
        // 验证timestamp 是否过期
        if (now - timestamp > 6 * 60 * 1000) {
            // 6 分钟 从配置中获取 时间越小越好
            throw new IllegalAccessException("请求时间超过规定范围时间");
        }
        // 获取请求参数或者头部信息，包括签名字段等
        String appId = request.getHeader("appId");
        String appKey = request.getHeader("appKey");
        // 所有数据的签名信息
        String signature = request.getHeader("signature");
        if (StringUtils.isEmpty(appId) || StringUtils.isEmpty(appKey) || StringUtils.isEmpty(signature)) {
            throw new IllegalAccessException("没有 appId || appKey || signature");
        }

        // 从配置中拿到 appId + appKey 对应的 appSecret
        String appSecret = "123";
        if (StringUtils.isEmpty(appSecret)) {
            log.error("未找到appId对应的appSecret, appId:{}, appKey:{}", appId, appKey);
            throw new IllegalAccessException("未找到appId对应的appSecret:appId:" + appId + ",appKey:" + appKey);
        }
        // 加密获取 生成的signature 与入参signature 比较，验证参数是否中途被篡改

        // 入参转 treeMap
        Map<String, Object> treeMap = null;
        for (int i = 0; i < joinPoint.getArgs().length; i++) {
            Object object = joinPoint.getArgs()[i];
            if (object instanceof HttpServletRequest || object instanceof HttpServletResponse) {
                continue;
            }
            // 转为有序的TreeMap
            treeMap = JSONObject.parseObject(JSON.toJSONString(object), TreeMap.class);
        }

        // 验证是否重放请求
        String nonce = request.getHeader("nonce");
        // 查询redis是否存在nonce值 自增指令是否为1
//        throw new IllegalAccessException("Invalid signature"); // 签名无效，抛出异常

        // 其他校验 比如黑白名单

    }


}
