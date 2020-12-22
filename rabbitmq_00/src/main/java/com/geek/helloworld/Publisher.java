package com.geek.helloworld;

import com.geek.helloworld.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * sender。
 *
 * @author geek
 */
public class Publisher {

    /**
     * 发布消息。
     *
     * @throws IOException
     */
    @Test
    public void testSendMessages() throws IOException, TimeoutException {
/*
        // 创建连接工厂。
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 设置连接 RabbitMQ 主机。
        connectionFactory.setHost("192.168.142.161");
        // 连接 RabbitMQ 主机端口。
        connectionFactory.setPort(5672);
        // 虚拟主机。
        connectionFactory.setVirtualHost("/amqp_test");
        // 设置访问虚拟主机的用户名和密码。
        connectionFactory.setUsername("geek");
        connectionFactory.setPassword("123.");

        // 获取连接对象。
        Connection connection = connectionFactory.newConnection();
*/

        // 通过工具获取对象。
        Connection connection = RabbitMQUtils.getConnection();

        // 获取连接中通道对象。
        Channel channel = connection.createChannel();

        // 通道声明对应的消息队列。
        // 参数 1：queue。队列名称，如果队列不存在，会自动创建。
        // 参数 2：durable。队列特性：是否持久化。只是队列持久化，队列中的消息不会持久化。false ~ RabbitMQ 重启队列就没了。
        // 参数 3：exclusive。是否独占连接。
        // 参数 4：autoDelete。是否在消费完成后删除队列。（要在消费者释放连接后才删除）。
        // 参数 5：argument。额外 / 附加参数。
        channel.queueDeclare("hello", false, false, false, null);

        // 发布消息。
        // 参数 1：exchange。交换机名称。P/C 没有交换机。
        // 参数 2：routingKey。队列名称。
        // 参数 3：props。传递消息额外参数。MessageProperties.PERSISTENT_TEXT_PLAIN～队列中的消息持久化。
        // 参数 4：消息具体内容。字节形式。
        channel.basicPublish("", "hello", MessageProperties.PERSISTENT_TEXT_PLAIN, "hello".getBytes());

//        channel.close();
//        connection.close();
        // 通过工具关闭连接。
        RabbitMQUtils.closeConnectionAndChannel(channel, connection);
    }

}
