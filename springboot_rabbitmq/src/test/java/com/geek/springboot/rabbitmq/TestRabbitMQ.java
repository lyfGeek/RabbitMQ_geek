package com.geek.springboot.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = SpringbootRabbitmqApplication.class)
@RunWith(SpringRunner.class)
public class TestRabbitMQ {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    // topic 动态路由。
    @Test
    public void testTopic() {
        rabbitTemplate.convertAndSend("topics", "user.save", "user.save 动态路由消息。");
    }

    // routing 路由模式。
    @Test
    public void testRoute() {
        rabbitTemplate.convertAndSend("directs", "info", "routeKey 为 info 的路由信息。");
    }

    // Publish / Subscribe。fanout。
    @Test
    public void testFanout() {
        rabbitTemplate.convertAndSend("logs", "", "fanout 模型发送新消息。");
    }

    // worker。
    @Test
    public void testWorker() {
        for (int i = 0; i < 5; i++) {
            rabbitTemplate.convertAndSend("worker", "Worker Queues ~ " + i);
        }
    }

    // Hello World。
    @Test
    public void helloWorld() {
        rabbitTemplate.convertAndSend("hello", "Hello World.");
        // convertAndSend(String routingKey, Object object); 会把 Object 转化为 byte。
    }

}
