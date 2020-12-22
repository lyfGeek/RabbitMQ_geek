package com.geek;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-rabbitmq-publisher.xml")
public class TTLTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * time to live.
     * - 队列统一过期。
     * - 消息单独过期。
     */
    @Test
    public void testTtl01() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("test_exchange_ttl", "ttl.hehe", "message ttl...");
        }
    }

    /**
     * time to live.
     * - 消息单独过期。
     */
    @Test
    public void testTtl02() {
        rabbitTemplate.convertAndSend("test_exchange_ttl", "ttl.hehe", "message ttl...", new MessagePostProcessor() {
            /**
             * 消息后处理对象，设置消息参数。
             * @param message
             * @return
             * @throws AmqpException
             */
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                // 设置 message 信息。过期时间。队列改为 100s 过期，便于观察。
                message.getMessageProperties().setExpiration("10000");
                // 返回该消息。
                return message;
            }
        });
    }

    /**
     * time to live.
     * - 消息单独过期。
     * 消息过期后，只有消息在队列顶端，都会判断其是否过期，再移除。
     */
    @Test
    public void testTtl03() {
        for (int i = 0; i < 10; i++) {
            if (i == 5) {
                // 消息单独过期。10 秒后队列消息还是 10，因为 ta 不在队列顶端。
                rabbitTemplate.convertAndSend("test_exchange_ttl", "ttl.hehe", "message ttl...", new MessagePostProcessor() {
                    /**
                     * 消息后处理对象，设置消息参数。
                     *
                     * @param message
                     * @return
                     * @throws AmqpException
                     */
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        // 设置 message 信息。过期时间。队列改为 100s 过期，便于观察。
                        message.getMessageProperties().setExpiration("10000");
                        // 返回该消息。
                        return message;
                    }
                });
            } else {
                rabbitTemplate.convertAndSend("test_exchange_ttl", "ttl.hehe", "message ttl...");
            }
        }
    }

}
