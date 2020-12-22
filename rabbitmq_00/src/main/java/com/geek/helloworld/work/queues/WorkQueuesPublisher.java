package com.geek.helloworld.work.queues;

import com.geek.helloworld.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @author geek
 */
public class WorkQueuesPublisher {

    public static void main(String[] args) throws IOException {
        // 获取连接对象。
        Connection connection = RabbitMQUtils.getConnection();
        // 获取通道对象，
        Channel channel = connection.createChannel();
        // 通过通道声明对象。
        channel.queueDeclare("work", true, false, false, null);

        // 发布消息。
        for (int i = 0; i < 10; i++) {
            channel.basicPublish("", "work", null, ("hello work queues ~ " + i).getBytes());
        }

        RabbitMQUtils.closeConnectionAndChannel(channel, connection);
    }

}
