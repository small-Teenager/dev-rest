package com.dev.rest.controller.apply;

import com.dev.rest.config.DelayQueueMQConfig;
import com.dev.rest.config.RabbitMQProducerConfig;
import com.dev.rest.mq.rabbit.MessageProducer;
import com.dev.rest.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/apply/rabbitmq")
public class RabbitMQController {

    private static final Logger log = LoggerFactory.getLogger(RabbitMQController.class);
    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    private RabbitTemplate rabbitTemplate;

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
        log.info("delay queue producer time is:{},message:{}", LocalDateTime.now(), message);
        rabbitTemplate.convertAndSend(DelayQueueMQConfig.SIMPLE_EXCHANGE_NAME, DelayQueueMQConfig.SIMPLE_ROUTING_KEY, message);
        return ApiResponse.success(true);
    }

    @PostMapping("/pub-sub/produce/{message}")
    public ApiResponse<Boolean> pubSub(@PathVariable(value = "message") @NotNull String message) {
        log.info("publish-subscribe producer time is:{},message:{}", LocalDateTime.now(), message);
        //由于实现的是fanout模式（广播模式），不需要路由key，所有的消费者都可以进行监听和消费
        rabbitTemplate.convertAndSend(RabbitMQProducerConfig.PUB_SUB_EXCHANGE_NAME, "", message);
        return ApiResponse.success(true);
    }
}