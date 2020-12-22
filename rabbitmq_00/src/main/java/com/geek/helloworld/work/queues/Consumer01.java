package com.geek.helloworld.work.queues;

import com.geek.helloworld.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author geek
 */
public class Consumer01 {

    public static void main(String[] args) throws IOException {
        // 获取连接。
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.basicQos(1);// 每次只消费一个消息。
        channel.queueDeclare("work", true, false, false, null);
        // autoAck ~ false ~ 不自动确认消息。
        channel.basicConsume("work", false, new DefaultConsumer(channel) {
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
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("消费者～1～" + new String(body));
                // 参数 1：deliveryTag。确认消息队列中的哪个消息。
                // 参数 2：multiple。是否开启多个消息同时确认。
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });

        // 不关闭就可以一直监听。关闭只消费一次。
//        channel.close();
//        connection.close();
    }

}
