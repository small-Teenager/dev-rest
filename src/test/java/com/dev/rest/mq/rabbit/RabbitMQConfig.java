package com.dev.rest.mq.rabbit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(value = "local")
public class RabbitMQConfig {
    @Value("${spring.rabbitmq.addresses}")
    public String host;

    @Value("${spring.rabbitmq.port}")
    public Integer port;

    @Value("${spring.rabbitmq.virtual-host}")
    public String virtualHost;

    @Value("${spring.rabbitmq.username}")
    public String username;

    @Value("${spring.rabbitmq.password}")
    public String password;
}
