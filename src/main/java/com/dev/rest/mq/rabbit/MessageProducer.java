package com.dev.rest.mq.rabbit;

import com.dev.rest.config.DelayQueueMQConfig;
import com.dev.rest.config.RabbitMQProducerConfig;
import com.dev.rest.dto.MessageDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageProducer {
    private static final Logger log = LoggerFactory.getLogger(MessageProducer.class);

    private static final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     *
     * @param content
     */
    public void sendMessageToMQ(String content) {
        log.info("produce message:{}", content);
        MessageDTO dto = new MessageDTO();
        dto.setContent(content);
        rabbitTemplate.convertAndSend(this.format(dto));
    }

    private String format(Object pojo) {
        try {
            return mapper.writeValueAsString(pojo);
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException, pojo = {}", pojo, e);
            return "{}";
        }
    }

    public void delayMessageSend(String msg) {
        log.info("delay queue producer time is:{},message:{}", LocalDateTime.now(), msg);
        rabbitTemplate.convertAndSend(DelayQueueMQConfig.SIMPLE_EXCHANGE_NAME, DelayQueueMQConfig.SIMPLE_ROUTING_KEY, msg);
    }

    public void pubSubMessageSend(String msg) {
        log.info("publish-subscribe producer time is:{},message:{}", LocalDateTime.now(), msg);
        //由于实现的是fanout模式（广播模式），不需要路由key，所有的消费者都可以进行监听和消费
        rabbitTemplate.convertAndSend(RabbitMQProducerConfig.PUB_SUB_EXCHANGE_NAME, "", msg);
    }

    public void priorityMessageSend(String msg, int priority) {
        log.info("priority producer priority is:{}, time is:{},message:{}",priority, LocalDateTime.now(), msg);
        MessagePostProcessor messagePostProcessor = message -> {
            // 设置消息的优先级
            message.getMessageProperties().setPriority(priority);
            return message;
        };
        rabbitTemplate.convertAndSend(RabbitMQProducerConfig.PRIORITY_EXCHANGE, RabbitMQProducerConfig.PRIORITY_ROUTING_KEY, msg, messagePostProcessor);
    }
}
