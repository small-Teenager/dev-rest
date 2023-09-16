package com.dev.rest.listener;

import com.alibaba.fastjson.JSON;
import com.dev.rest.config.RedisLimitAOP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

    private static final Logger log = LoggerFactory.getLogger(RedisKeyExpirationListener.class);

    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("message:{},pattern:{}", JSON.toJSONString(message), JSON.toJSONString(pattern));
        String expiredKey = message.toString();
        log.info("监听到Redis key：{} 已过期", expiredKey);
    }
}