package com.dev.rest.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQProducerConfig {

    /*------- fanout publish-subscribe ---------*/
    public static final String PUB_SUB_EXCHANGE_NAME = "fanout-order-exchange";
    public static final String PUB_SUB_SMS_QUEUE = "sms-fanout-queue";
    public static final String PUB_SUB_EMAIL_QUEUE = "email-fanout-queue";

    /**
     * 1.
     * 声明交换机
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        /**
         * FanoutExchange的参数说明:
         * 1. 交换机名称
         * 2. 是否持久化 true：持久化，交换机一直保留 false：不持久化，用完就删除
         * 3. 是否自动删除 false：不自动删除 true：自动删除
         */
        return new FanoutExchange(PUB_SUB_EXCHANGE_NAME, true, false);
    }

    /**
     * 声明队列
     * @return
     */
    @Bean
    public Queue smsQueue() {
        /**
         * Queue构造函数参数说明
         * 1. 队列名
         * 2. 是否持久化 true：持久化 false：不持久化
         */
        return new Queue(PUB_SUB_SMS_QUEUE, true);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(PUB_SUB_EMAIL_QUEUE, true);
    }

    /**
     * 队列与交换机绑定
     */
    @Bean
    public Binding smsBinding() {
        return BindingBuilder.bind(smsQueue()).to(fanoutExchange());
    }

    @Bean
    public Binding emailBinding() {
        return BindingBuilder.bind(emailQueue()).to(fanoutExchange());
    }

    /**
     * 配置生产者rabbitTemplate
     * 生产者只需要配置exchange和routingKey
     */
    @Bean(name = "pointRabbitTemplate")
    public RabbitTemplate pointRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange("EXCHANGE_NAME");
        rabbitTemplate.setRoutingKey("ROUTING_KEY");
        rabbitTemplate.setMandatory(true);
        return rabbitTemplate;
    }

    /*------- priority queue ---------*/
    public static final String PRIORITY_ROUTING_KEY = "priority.key";
    public static final String PRIORITY_QUEUE = "priority.queue";
    public static final String PRIORITY_EXCHANGE = "priority.exchange";

    /**
     * priority exchange
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(PRIORITY_EXCHANGE);
    }


    /**
     * priority queue
     */
    @Bean
    public Queue priorityQueue() {
        Map<String, Object> args = new HashMap<>();
        // 设置队列最大优先级
        // To declare a priority queue, use the x-max-priority optional queue argument.
        // This argument should be a positive integer between 1 and 255,
        // indicating the maximum priority the queue should support.
        args.put("x-max-priority", 10);
        return QueueBuilder.durable(PRIORITY_QUEUE).withArguments(args).build();
    }

    /**
     * priority binding exchange
     */
    @Bean
    public Binding priorityBinding(Queue priorityQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(priorityQueue).to(directExchange).with(PRIORITY_ROUTING_KEY);
    }

}