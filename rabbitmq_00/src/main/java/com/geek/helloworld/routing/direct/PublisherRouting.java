package com.geek.helloworld.routing.direct;

import com.geek.helloworld.utils.RabbitMQUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @author geek
 */
public class PublisherRouting {

    public static void main(String[] args) throws IOException {
        // 获取连接对象。
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        String exchangeName = "test_direct";
        // 通道声明交换机。
        // 参数 1：交换机名称。
        // 参数 2：交换机类型。fanout ——> 广播类型。发送消息到每一个与之绑定的队列。
//        channel.exchangeDeclare("logs", "fanout");
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT, true, false, false, null);

/*
        AMQP.Exchange.DeclareOk exchangeDeclare (String exchange,  // 交换机名称。
            String type,  // 交换机类型。public enum BuiltinExchangeType {
            boolean durable,  // 是否持久化。
            boolean autoDelete,  // 自动删除。
            boolean internal,  // 内部使用，一般 false。
            Map<String, Object> arguments  // 参数。
        ) throws IOException;
*/

        String queue1Name = "test_direct_queue1";
        String queue2Name = "test_direct_queue2";
        // 创建队列。
        channel.queueDeclare(queue1Name, true, false, false, null);
        channel.queueDeclare(queue2Name, true, false, false, null);

        // 绑定队列和交换机。
        // 队列 1 绑定 error。
        channel.queueBind(queue1Name, exchangeName, "info");
        // 队列 2 绑定 info warning error。
        channel.queueBind(queue2Name, exchangeName, "info");
        channel.queueBind(queue2Name, exchangeName, "warning");
        channel.queueBind(queue2Name, exchangeName, "error");

        // 发送消息。
        // 参数 1：exchange。交换机。
        // 参数 2：routingKey。路由 key。广播模式不需要路由 key。
        // 参数 3：props。附加参数。
        // 参数 4：消息。
        channel.basicPublish(exchangeName, "error", null, "日志信息：张三调用了 findAll(); 方法...日志级别 ~ info。".getBytes());

        // 释放资源。
        RabbitMQUtils.closeConnectionAndChannel(channel, connection);
    }

}
