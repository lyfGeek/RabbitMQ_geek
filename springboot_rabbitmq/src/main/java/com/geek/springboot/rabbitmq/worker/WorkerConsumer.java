package com.geek.springboot.rabbitmq.worker;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author geek
 */
@Component
public class WorkerConsumer {

    // 一个消费者。
    @RabbitListener(queuesToDeclare = @Queue("worker"))
    public void consumer(String message) {
        System.out.println("message = " + message);
    }

    // 一个消费者。
    @RabbitListener(queuesToDeclare = @Queue("worker"))
    public void consumer02(String message) {
        System.out.println("message02 = " + message);
    }

}
