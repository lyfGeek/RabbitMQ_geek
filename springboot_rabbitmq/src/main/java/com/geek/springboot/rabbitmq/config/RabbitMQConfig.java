package com.geek.springboot.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author geek
 */
@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "boot_topic_exchange";
    public static final String QUEUE_NAME = "boot_queue";

    /**
     * 交换机。
     *
     * @return
     */
    @Bean("bootExchange")
    public Exchange bootExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME)
                .durable(true)
                .build();
    }

    /**
     * 队列。
     *
     * @return
     */
    @Bean("bootQueue")
    public Queue bootQueue() {
        return QueueBuilder
                .durable(QUEUE_NAME)
                .build();
    }

    /**
     * 队列和交换机绑定关系。
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding bindQueueExchange(@Qualifier("bootQueue") Queue queue, @Qualifier("bootExchange") Exchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with("boot.#")
                .noargs();
    }

}
