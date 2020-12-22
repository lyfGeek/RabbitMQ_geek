package com.geek.springboot.rabbitmq.route;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author geek
 */
@Component
public class RouteConsumer {

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,// 创建临时队列。
                    exchange = @Exchange(value = "directs", type = "direct"),// 自定交换机名称和类型。
                    key = {"info", "error", "warn"}
            )
    })
    public void receive01(String message) {
        System.out.println("message1 = " + message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,// 创建临时队列。
                    exchange = @Exchange(value = "directs", type = "direct"),// 自定交换机名称和类型。
                    key = {"error"}
            )
    })
    public void receive02(String message) {
        System.out.println("message2 = " + message);
    }

}
