package com.geek;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试死信消息。
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq-publisher.xml")
public class DlxTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 测试死信 ~ 过期时间。
     * // 一开始 test_exchange_dlx 有一条消息。
     * 10s 后 test_queue_dlx 消息到 queue_dlx。
     */
    @Test
    public void testDlx() {
        rabbitTemplate.convertAndSend("test_exchange_dlx", "test.dlx.geek", "我是一条消息，我会死吗。");
    }

    /**
     * 测试死信 ~ 长度限制。
     * // 10 条消息进入 test_exchange_dlx。
     * 10 条消息进入 queue_dlx。
     * 10 s 后全部到 queue_dlx。
     */
    @Test
    public void testDlx2() {
        for (int i = 0; i < 20; i++) {
            rabbitTemplate.convertAndSend("test_exchange_dlx", "test.dlx.geek", "我是一条消息，我会死吗。");
        }
    }

    /**
     * 消息拒收。
     * <p>
     * queue_dlx 消息 +1。
     */
    @Test
    public void testDlx3() {
        rabbitTemplate.convertAndSend("test_exchange_dlx", "test.dlx.geek", "我是一条消息，我会死吗。");
    }

}
