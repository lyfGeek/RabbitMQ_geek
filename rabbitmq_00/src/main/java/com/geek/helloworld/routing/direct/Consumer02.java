package com.geek.helloworld.routing.direct;

import com.geek.helloworld.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author geek
 */
public class Consumer02 {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();

        Channel channel = connection.createChannel();

//        String exchangeName = "logs_direct";
        String exchangeName = "test_direct";
        String queue1Name = "test_direct_queue1";
        String queue2Name = "test_direct_queue2";

        // 通道声明交换机，类型 Direct。
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);

        // 创建一个临时队列。
        String queueName = channel.queueDeclare().getQueue();

        // 基于 route key 绑定队列和交换机。
        channel.queueBind(queueName, exchangeName, "info");
        channel.queueBind(queueName, exchangeName, "error");
        channel.queueBind(queueName, exchangeName, "warning");

        // 获取消费的消息。
        channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("消费者 02～" + new String(body));
                System.out.println("将信息存储到数据库。");
            }
        });
    }

}
