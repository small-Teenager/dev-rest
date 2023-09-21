package com.dev.rest.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQProducerConfig {

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
}