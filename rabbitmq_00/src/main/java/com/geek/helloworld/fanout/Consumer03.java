package com.geek.helloworld.fanout;

import com.geek.helloworld.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author geek
 */
public class Consumer03 {

    public static void main(String[] args) throws IOException {
        // 获取连接对象。
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        // 通道声明交换机。
        channel.exchangeDeclare("logs", "fanout");

        // 临时队列。
        String queueName = channel.queueDeclare().getQueue();

        // 绑定交换机的队列。
        channel.queueBind(queueName, "logs", "");

        // 消费消息。
        channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
            /**
             * No-op implementation of {@link Consumer#handleDelivery}.
             *
             * @param consumerTag
             * @param envelope
             * @param properties
             * @param body
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("消费者～3～" + new String(body));
            }
        });
    }

}
