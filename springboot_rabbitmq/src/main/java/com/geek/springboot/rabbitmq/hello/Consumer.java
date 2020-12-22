package com.geek.springboot.rabbitmq.hello;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author geek
 */
@Component
@RabbitListener(queuesToDeclare = @Queue("hello"))// 默认 持久化，非独占，不自动删除。
//@RabbitListener(queuesToDeclare = @Queue(value = "hello", durable = "true", exclusive = "false", autoDelete = "false"))
public class Consumer {

    @RabbitHandler
    public void receive(String message) {
        System.out.println("~ ~ ~ ~ ~ ~ ~");
        System.out.println("message = " + message);
        System.out.println("~ ~ ~ ~ ~ ~ ~");
    }

}
