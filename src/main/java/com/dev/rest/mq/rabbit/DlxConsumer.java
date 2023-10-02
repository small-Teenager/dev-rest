package com.dev.rest.mq.rabbit;

import com.dev.rest.config.DelayQueueMQConfig;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * rabbitmq 实现延迟队列消费者(实际消费死信队列)
 */
@Component
public class DlxConsumer {
    private static final Logger log = LoggerFactory.getLogger(DlxConsumer.class);

    @RabbitListener(queues = DelayQueueMQConfig.DLX_QUEUE_NAME)
    public void onMessage(Message message, Channel channel) {
        log.info("delay queue consumer time is:{}", LocalDateTime.now());
        log.info("delay queue consumed message: {},channel:{}", message, channel);
    }
}