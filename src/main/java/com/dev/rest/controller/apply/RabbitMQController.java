package com.dev.rest.controller.apply;


import com.dev.rest.mq.rabbit.MessageProducer;
import com.dev.rest.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/apply/rabbitmq")
public class RabbitMQController {

    @Autowired
    private MessageProducer messageProducer;

    /**
     * 简单队列模型
     *
     * @param content
     * @return
     */
    @PostMapping("/sendMQMessage")
    public ApiResponse<Boolean> sendMQMessage(@RequestParam String content) {
        messageProducer.sendMessageToMQ(content);
        return ApiResponse.success(true);
    }

    /**
     * rabbitmq delay queue
     *
     * @param message
     * @return
     */
    @PostMapping("/delay-queue/produce/{message}")
    public ApiResponse<Boolean> producer(@PathVariable(value = "message") @NotNull String message) {
        messageProducer.delayMessageSend(message);
        return ApiResponse.success(true);
    }

    @PostMapping("/pub-sub/produce/{message}")
    public ApiResponse<Boolean> pubSub(@PathVariable(value = "message") @NotNull String message) {
        messageProducer.pubSubMessageSend(message);
        return ApiResponse.success(true);
    }

    @PostMapping("/priority-queue/produce")
    public ApiResponse<Boolean> priority() {
        for (int i = 1; i <= 10; i++) {
            String msg = String.format("这是第 %d 条消息，优先级是 %d", i, i);
            messageProducer.priorityMessageSend(msg, i);
        }
        return ApiResponse.success(true);
    }
}