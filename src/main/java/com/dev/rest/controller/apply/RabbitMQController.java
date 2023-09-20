package com.dev.rest.controller.apply;

import com.dev.rest.mq.rabbit.MessageProducer;
import com.dev.rest.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apply/rabbitmq")
public class RabbitMQController {
    @Autowired
    private MessageProducer messageProducer;

    /**
     * 简单队列模型
     * @param content
     * @return
     */
    @PostMapping("/sendMQMessage")
    public ApiResponse<Boolean> sendMQMessage(@RequestParam String content) {
        messageProducer.sendMessageToMQ(content);
        return ApiResponse.success(true);
    }
}