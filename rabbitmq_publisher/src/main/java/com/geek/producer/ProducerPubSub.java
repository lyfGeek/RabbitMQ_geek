package com.geek.producer;

import com.geek.producer.utils.RabbitMQUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author geek
 */
public class ProducerPubSub {

    public static void main(String[] args) throws IOException, TimeoutException {

        // 获取连接对象。
        Connection connection = RabbitMQUtils.getConnection();

        Channel channel = connection.createChannel();

        String exchangeName = "test_fanout";
/*
        AMQP.Exchange.DeclareOk exchangeDeclare (String exchange,  // 交换机名称。
            String type,  // 交换机类型。public enum BuiltinExchangeType {
            boolean durable,  // 是否持久化。
            boolean autoDelete,  // 自动删除。
            boolean internal,  // 内部使用，一般 false。
            Map<String, Object> arguments  // 参数。
        ) throws IOException;
*/
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT, true, false, false, null);

        String queue1Name = "test_fanout_queue1";
        String queue2Name = "test_fanout_queue2";
        // 声明队列。
        channel.queueDeclare(queue1Name, true, false, false, null);
        channel.queueDeclare(queue2Name, true, false, false, null);

        // 绑定队列和交换机。
        // 交换机类型 ~ fanout，routingKey 为 ""。
        channel.queueBind(queue1Name, exchangeName, "");
        channel.queueBind(queue2Name, exchangeName, "");

        // 发送消息。
        // 参数 1：exchange。交换机。
        // 参数 2：routingKey。路由 key。广播模式不需要路由 key。
        // 参数 3：props。附加参数。
        // 参数 4：消息。
        String body = "日志信息 ~ 张三调用了 findAll(); 方法...日志级别 ~ info...";
        channel.basicPublish(exchangeName, "", null, body.getBytes());

        // 释放资源。
        RabbitMQUtils.closeConnectionAndChannel(channel, connection);
    }

}
