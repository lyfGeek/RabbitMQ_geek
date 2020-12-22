package com.geek.consumer;

import com.geek.consumer.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author geek
 */
public class ConsumerTopic01 {

    public static void main(String[] args) throws IOException {

        Connection connection = RabbitMQUtils.getConnection();

        // 创建 Channel。
        Channel channel = connection.createChannel();

        String queue1Name = "test_topic_queue1";
        String queue2Name = "test_topic_queue2";
        /*
        basicConsume(String queue, boolean autoAck, Consumer callback)
        参数：
            1. queue：队列名称
            2. autoAck：是否自动确认
            3. callback：回调对象

         */
        // 消费消息。
        // 参数 1：queue。队列名称。
        // 参数 2：autoAck。开启消息自动确认机制。
        // 参数 3：消费时的回调接口。
        channel.basicConsume(queue1Name, true, new DefaultConsumer(channel) {
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
                /*System.out.println("consumerTag = " + consumerTag);
                System.out.println("envelope = " + envelope);
                System.out.println("properties = " + properties);*/
                System.out.println("body = " + new String(body));
                System.out.println("将日志信息保存到数据库。");
            }
        });

        // 不关闭就可以一直监听。关闭只消费一次。
//        channel.close();
//        connection.close();
    }

}
