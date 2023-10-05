package com.dev.rest.delayqueue;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrderPaymentTimeoutHandle implements RedissonDelayQueueHandle<Map> {

    private static final Logger log = LoggerFactory.getLogger(OrderPaymentTimeoutHandle.class);
    @Override
    public void execute(Map map) {
        log.info("收到订单支付超时延迟消息: {}", map);
        // TODO 订单支付超时，自动取消订单处理业务...
    }

}
