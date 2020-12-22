package com.geek.helloworld.routing.topics;

import com.geek.helloworld.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @author geek
 */
public class Publisher {

    public static void main(String[] args) throws IOException {
        // 获取连接对象。
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        // 声明交换机以及交换机类型 ～ topic。
        channel.exchangeDeclare("topics", "topic");

        // 发布消息。
        String routingKey = "user.save";

        channel.basicPublish("topics", routingKey, null, ("topic 动态路由模型。routingKey～" + routingKey).getBytes());

        // 释放资源。
        RabbitMQUtils.closeConnectionAndChannel(channel, connection);
    }

}
