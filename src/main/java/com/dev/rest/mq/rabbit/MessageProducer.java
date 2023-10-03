package com.dev.rest.mq.rabbit;

import com.dev.rest.dto.MessageDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {
    private static final Logger log = LoggerFactory.getLogger(MessageProducer.class);

    private static final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private RabbitTemplate pointRabbitTemplate;

    /**
     * 发送消息
     *
     * @param content
     */
    public void sendMessageToMQ(String content) {
        log.info("produce message:{}", content);
        MessageDTO dto = new MessageDTO();
        dto.setContent(content);
        pointRabbitTemplate.convertAndSend(this.format(dto));
    }

    private String format(Object pojo) {
        try {
            return mapper.writeValueAsString(pojo);
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException, pojo = {}", pojo, e);
            return "{}";
        }
    }
}
