package com.dev.rest.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * rabbitmq
 * 死信交换机+消息超时时间 实现delay queue
 * (也可以用rabbitmq_delayed_message_exchange 实现)
 */
@Configuration
public class DelayQueueMQConfig {

    /*-------simple queue---------*/
    public static final String SIMPLE_QUEUE_NAME = "simple_queue_name";
    public static final String SIMPLE_EXCHANGE_NAME = "simple_exchange_name";
    public static final String SIMPLE_ROUTING_KEY = "simple_routing_key";

    /*-------dlx queue---------*/
    public static final String DLX_QUEUE_NAME = "dlx_queue_name";
    public static final String DLX_EXCHANGE_NAME = "dlx_exchange_name";
    public static final String DLX_ROUTING_KEY = "dlx_routing_key";


    /**
     * 普通消息队列
     *
     * @return
     */
    @Bean
    public Queue simpleQueue() {
        Map<String, Object> args = new HashMap<>();
        //设置消息过期时间 30s
        args.put("x-message-ttl", 1000 * 30);
        //设置死信交换机
        args.put("x-dead-letter-exchange", DLX_EXCHANGE_NAME);
        //设置死信 routing_key
        args.put("x-dead-letter-routing-key", DLX_ROUTING_KEY);
        return new Queue(SIMPLE_QUEUE_NAME, true, false, false, args);
    }

    /**
     * 普通交换机
     *
     * @return
     */
    @Bean
    public DirectExchange simpleExchange() {
        return new DirectExchange(SIMPLE_EXCHANGE_NAME, true, false);
    }

    /**
     * 绑定普通队列和与之对应的交换机
     *
     * @return
     */
    @Bean
    public Binding simpleBinding() {
        return BindingBuilder.bind(simpleQueue()).to(simpleExchange()).with(SIMPLE_ROUTING_KEY);
    }

    /**
     * 死信队列
     *
     * @return
     */
    @Bean
    Queue dlxQueue() {
        return new Queue(DLX_QUEUE_NAME, true, false, false);
    }

    /**
     * 死信交换机
     *
     * @return
     */
    @Bean
    public DirectExchange dlxExchange() {
        return new DirectExchange(DLX_EXCHANGE_NAME, true, false);
    }

    /**
     * 绑定死信队列和死信交换机
     *
     * @return
     */
    @Bean
    public Binding dlxBinding() {
        return BindingBuilder.bind(dlxQueue()).to(dlxExchange()).with(DLX_ROUTING_KEY);
    }


}
