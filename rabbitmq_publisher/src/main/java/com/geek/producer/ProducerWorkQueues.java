package com.geek.producer;

import com.geek.producer.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author geek
 */
public class ProducerWorkQueues {

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = RabbitMQUtils.getConnection();

        // 创建 Channel。
        Channel channel = connection.createChannel();

        // 创建队列 Queue。
        /*
Queue.DeclareOk queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete,
                                 Map<String, Object> arguments) throws IOException;
        参数：
            queue ~ 队列名称。
            durable ~ 是否持久化，当 mq 重启之后，还在。
            exclusive ~
                是否独占。只能有一个消费者监听这队列。
                当 Connection 关闭时，是否删除队列。
            autoDelete ~ 是否自动删除。当没有 Consumer 时，自动删除队列。
            arguments ~ 参数。
         */
        // 如果没有一个名字叫 work_queues 的队列，则会创建该队列，如果有则不会创建。
        channel.queueDeclare("work_queues", true, false, false, null);
        /*
    void basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body) throws IOException;
        参数：
            exchange ~ 交换机名称。简单模式下交换机会使用默认的 ""。
            routingKey ~ 路由名称。简单模式写队列名。
            props ~ 配置信息。
            body ~ 发送消息数据。
         */
        for (int i = 1; i <= 10; i++) {
            String body = i + " ~ hello rabbitmq ~~~ ";
            // 发送消息。
            channel.basicPublish("", "work_queues", null, body.getBytes());
        }
        // 释放资源。
        channel.close();
        connection.close();
    }

}
