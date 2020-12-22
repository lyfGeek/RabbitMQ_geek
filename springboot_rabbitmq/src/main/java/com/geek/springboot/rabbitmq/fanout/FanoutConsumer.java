package com.geek.springboot.rabbitmq.fanout;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author geek
 */
@Component
public class FanoutConsumer {

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,// 创建临时队列。
                    exchange = @Exchange(value = "logs", type = "fanout")// 绑定的交换机。
            )
    })
    public void receiver01(String message) {
        System.out.println("message01 = " + message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,// 创建临时队列。
                    exchange = @Exchange(value = "logs", type = "fanout")// 绑定的交换机。
            )
    })
    public void receiver02(String message) {
        System.out.println("message02 = " + message);
    }

}
