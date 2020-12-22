package com.geek;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试延时队列。
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq-publisher.xml")
public class DelayTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testDelay() throws InterruptedException {
        // 发送订单消息。在订单系统中，下单成功后，发送消息。
        rabbitTemplate.convertAndSend("order_exchange", "order.msg", "订单信息：id = 1.");

        // 打印倒计时 10 s。
        for (int i = 10; i > 0; i--) {
            System.out.println(i);
            Thread.sleep(1000);
        }
    }

}
