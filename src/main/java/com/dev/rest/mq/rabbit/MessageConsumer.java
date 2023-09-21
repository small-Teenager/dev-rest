package com.dev.rest.mq.rabbit;

import com.dev.rest.config.RabbitMQConsumerConfig;
import com.dev.rest.dto.MessageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MessageConsumer {
    private static final Logger log = LoggerFactory.getLogger(MessageConsumer.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    /**
     * 监听消息队列，队列名称QUEUE_NAME
     * 通过brokerContainerFactory获取到对应的queue
     */
    @RabbitListener(queues = "QUEUE_NAME", containerFactory = "brokerContainerFactory")
    public void onMessage(Message message, Channel channel) throws Exception {
        log.info("Consumed message: {},channel:{}", message,channel);
        try {
            MessageDTO dto = parse(new String(message.getBody()), MessageDTO.class);
            log.info("Consumed message content: {}", dto);
            // 由于之前配置的手动ack，需要手动回调rabbitMQ服务器，通知已经完成消费
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch(Exception e) {
            log.error("Consumed error:{}", e);
            // 由于之前配置的手动ack，需要手动回调rabbitMQ服务器，通知出现问题
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }

    public static <T> T parse(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            log.error("IOException, json = {}, clazz = {}", json, clazz, e);
            try {
                return clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e1) {
                log.error("InstantiationException or IllegalAccessException, clazz = {}", clazz, e1);
                return null;
            }
        }
    }
}