package com.geek.consumer.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author geek
 */
public class RabbitMQUtils {

    private static ConnectionFactory connectionFactory;

    // 重量级资源。类加载时执行，只执行一次。
    static {
        // 创建连接工厂。
        connectionFactory = new ConnectionFactory();
        // 设置连接 RabbitMQ 主机。
        connectionFactory.setHost("192.168.142.161");
        // 连接 RabbitMQ 主机端口。
        connectionFactory.setPort(5672);
        // 虚拟主机。
        connectionFactory.setVirtualHost("/amqp_test");
        // 设置访问虚拟主机的用户名和密码。
        connectionFactory.setUsername("geek");
        connectionFactory.setPassword("123.");
    }

    /**
     * 提供连接对象。
     *
     * @return
     */
    public static Connection getConnection() {
        try {
            return connectionFactory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 释放资源工具。
     *
     * @param channel
     * @param connection
     */
    public static void closeConnectionAndChannel(Channel channel, Connection connection) {
        if (channel != null) {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
