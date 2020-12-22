package com.geek.helloworld.routing.topics;

import com.geek.helloworld.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author geek
 */
public class Consumer02 {

    public static void main(String[] args) throws IOException {

        // 获取连接。
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        //声明交换机以及交换机类型。
        channel.exchangeDeclare("topics", "topic");

        // 创建临时队列。
        String queue = channel.queueDeclare().getQueue();
        // 指定队列和交换机。动态通配符形式。RoutingKey。
        channel.queueBind(queue, "topics", "user.*");

        // 消费消息。
        channel.basicConsume(queue, true, new DefaultConsumer(channel) {
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
                System.out.println("消费者～02～" + new String(body));
            }
        });
    }

}
