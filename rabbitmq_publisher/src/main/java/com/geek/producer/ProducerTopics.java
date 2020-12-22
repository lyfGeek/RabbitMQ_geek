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
public class ProducerTopics {

    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = RabbitMQUtils.getConnection();

        // 创建 Channel。
        Channel channel = connection.createChannel();
        /*
        AMQP.Exchange.DeclareOk exchangeDeclare (String exchange,  // 交换机名称。
            String type,  // 交换机类型。public enum BuiltinExchangeType {
                DIRECT("direct"), ~ 定向。
                FANOUT("fanout"), ~ 扇形（广播），发送消息到每一个与之绑定队列。
                TOPIC("topic"), ~ 通配符的方式。
                HEADERS("headers"); ~ 参数匹配。
            boolean durable,  // 是否持久化。
            boolean autoDelete,  // 自动删除。
            boolean internal,  // 内部使用，一般 false。
            Map<String, Object> arguments  // 参数。
        ) throws IOException;
*/
        String exchangeName = "test_topic";
        //5. 创建交换机
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC, true, false, false, null);
        //6. 创建队列
        String queue1Name = "test_topic_queue1";
        String queue2Name = "test_topic_queue2";
        channel.queueDeclare(queue1Name, true, false, false, null);
        channel.queueDeclare(queue2Name, true, false, false, null);
        // 绑定队列和交换机。
        /*
    Queue.BindOk queueBind(String queue, String exchange, String routingKey) throws IOException;
        参数：
            queue ~ 队列名称。
            exchange ~ 交换机名称。
            routingKey ~ 路由键，绑定规则。
                如果交换机的类型为 fanout，routingKey 设置为 ""。
         */
        // routing key 系统的名称.日志的级别。
        // 需求：所有 error 级别的日志存入数据库，所有 order 系统的日志存入数据库>
        channel.queueBind(queue1Name, exchangeName, "#.error");
        channel.queueBind(queue1Name, exchangeName, "order.*");
        channel.queueBind(queue2Name, exchangeName, "*.*");

        String body = "日志信息 ~ 张三调用了 findAll(); 方法...日志级别：info...";
        // 发送消息。
//        channel.basicPublish(exchangeName, "order.error", null, body.getBytes());
        channel.basicPublish(exchangeName, "goods.info", null, body.getBytes());
        // 释放资源。
        channel.close();
        connection.close();
    }

}
