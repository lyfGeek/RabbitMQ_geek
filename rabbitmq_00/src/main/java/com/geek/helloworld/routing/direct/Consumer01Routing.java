package com.geek.helloworld.routing.direct;

import com.geek.helloworld.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author geek
 */
public class Consumer01Routing {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();

        Channel channel = connection.createChannel();

//        String exchangeName = "logs_direct";
        String exchangeName = "test_direct";
        String queue1Name = "test_direct_queue1";
        String queue2Name = "test_direct_queue2";

        // 获取消费的消息。
        channel.basicConsume(queue2Name, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                super.handleDelivery(consumerTag, envelope, properties, body);
                System.out.println("消费者 01～" + new String(body));
                System.out.println("将信息打印到控制台。");
            }
        });
    }

}
