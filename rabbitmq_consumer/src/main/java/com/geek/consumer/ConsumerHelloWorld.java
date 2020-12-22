package com.geek.consumer;

import com.geek.consumer.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author geek
 */
public class ConsumerHelloWorld {

    public static void main(String[] args) throws IOException {

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
        // 如果没有一个名字叫 hello_world 的队列，则会创建该队列，如果有则不会创建。
        channel.queueDeclare("hello_world", true, false, false, null);

        // 消费消息。
        // 参数 1：queue。队列名称。
        // 参数 2：autoAck。开启消息自动确认机制。
        // 参数 3：消费时的回调接口。
        channel.basicConsume("hello_world", true, new DefaultConsumer(channel) {
            /**
             * @param consumerTag   标识。
             * @param envelope      交换机、路由...信息。
             * @param properties    配置信息。
             * @param body          消息队列中取出的消息。（注：（异步的）必须用 main() 方法。Junit Test 不支持多线程模型的）。
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("consumerTag = " + consumerTag);
                System.out.println("envelope = " + envelope);
                System.out.println("properties = " + properties);
                System.out.println("body = " + new String(body));
                // consumerTag = amq.ctag-epbgB2Mc-twaMn0GQOx6sA
                //envelope = Envelope(deliveryTag=1, redeliver=false, exchange=, routingKey=hello_world)
                //properties = #contentHeader<basic>(content-type=null, content-encoding=null, headers=null, delivery-mode=null, priority=null, correlation-id=null, reply-to=null, expiration=null, message-id=null, timestamp=null, type=null, user-id=null, app-id=null, cluster-id=null)
                //body = hello rabbitmq ~~~
            }
        });
    }

    // 不关闭就可以一直监听。关闭只消费一次。
//        channel.close();
//        connection.close();

}
