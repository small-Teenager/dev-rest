package com.dev.rest.delayqueue;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrderTimeoutNotEvaluatedHandle implements RedissonDelayQueueHandle<Map> {

    private static final Logger log = LoggerFactory.getLogger(OrderTimeoutNotEvaluatedHandle.class);
    @Override
    public void execute(Map map) {
        log.info("收到订单超时未评价延迟消息:{}", map);
        // TODO 订单超时未评价，系统默认好评处理业务...

    }

}
