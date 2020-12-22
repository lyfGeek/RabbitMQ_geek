package com.geek;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq-publisher.xml")
public class PublishTest {

    // 注入 RabbitTemplate。
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testHelloWorld() {
        // 发送消息。
        rabbitTemplate.convertAndSend("spring_queue", "hello world spring...");
    }

    @Test
    public void testFanout() {
        // 发送消息。
        rabbitTemplate.convertAndSend("spring_fanout_exchange", "", "hello fanout spring...");
    }

    @Test
    public void testTopics() {
        // 发送消息。
        rabbitTemplate.convertAndSend("spring_topic_exchange", "heima.hehe.haha", "hello topics spring...");
    }


}
