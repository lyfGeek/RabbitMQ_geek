package com.geek.springbootrabbitmqconsumer.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author geek
 */
@Component
public class RabbitMQListener {

    @RabbitListener(queues = "boot_queue")
    public void listenerQueue(Message message) {
        System.out.println("message = " + message);
    }

}
