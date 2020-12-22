package com.geek.helloworld;

import com.geek.helloworld.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author geek
 */
public class Consumer {

    public static void main(String[] args) throws IOException {
        // 通过工具获取对象。
        Connection connection = RabbitMQUtils.getConnection();

        // 创建通道。
        Channel channel = connection.createChannel();

        // 通道声明队列。
        channel.queueDeclare("hello", false, false, false, null);
        // 参数要一致。
        // Caused by: com.rabbitmq.client.ShutdownSignalException: channel error; protocol method: #method<channel.close>(reply-code=405, reply-text=RESOURCE_LOCKED - cannot obtain exclusive access to locked queue 'hello' in vhost '/amqp_test'. It could be originally declared on another connection or the exclusive property value does not match that of the original declaration., class-id=50, method-id=10)

        // 消费消息。
        // 参数 1：queue。队列名称。
        // 参数 2：autoAck。开启消息自动确认机制。
        // 参数 3：消费时的回调接口。
        channel.basicConsume("hello", true, new DefaultConsumer(channel) {
            /**
             * @param consumerTag
             * @param envelope
             * @param properties
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
                // consumerTag = amq.ctag-241G8Sk2zGys_1QO9IxbxA
                //envelope = Envelope(deliveryTag=1, redeliver=false, exchange=, routingKey=hello)
                //properties = #contentHeader<basic>(content-type=text/plain, content-encoding=null, headers=null, delivery-mode=2, priority=0, correlation-id=null, reply-to=null, expiration=null, message-id=null, timestamp=null, type=null, user-id=null, app-id=null, cluster-id=null)
                //body = hello
            }
        });

        // 不关闭就可以一直监听。关闭只消费一次。
//        channel.close();
//        connection.close();
    }

}
