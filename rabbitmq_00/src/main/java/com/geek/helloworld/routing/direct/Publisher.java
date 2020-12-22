package com.geek.helloworld.routing.direct;

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
        // 连接创建通道。
        Channel channel = connection.createChannel();
        // 通过通道声明交换机。
        // 参数 1：交换机名称。
        // 参数 2：交换机类型。～direct。
        channel.exchangeDeclare("logs_direct", "direct");

        // 发送消息。
        String routingKey = "info";
        channel.basicPublish("logs_direct", routingKey, null, ("direct 类型路由器 ~" + routingKey + "~ 发布的消息。").getBytes());

        // 释放资源。
        RabbitMQUtils.closeConnectionAndChannel(channel, connection);
    }

}
