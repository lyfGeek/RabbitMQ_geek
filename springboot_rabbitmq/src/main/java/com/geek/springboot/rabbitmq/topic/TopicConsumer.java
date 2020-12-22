package com.geek.springboot.rabbitmq.topic;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author geek
 */
@Component
public class TopicConsumer {

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(type = "topic", name = "topics"),
                    key = {"user.save", "user.*"}
            )
    })
    public void receive01(String message) {
        System.out.println("message01 = " + message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(type = "topic", name = "topics"),
                    key = {"order.#", "product.#", "user.*"}
            )
    })
    public void receive02(String message) {
        System.out.println("message02 = " + message);
    }

}
