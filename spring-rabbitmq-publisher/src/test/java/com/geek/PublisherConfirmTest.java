package com.geek;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq-publisher.xml")
public class PublisherConfirmTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 确认模式。
     * <p>
     * - 开启确认模式。<rabbit:connection-factory publisher-confirms="true"/>
     * - 在 RabbitTemplate 定义 ConfirmCallBack().confirm(); 回调函数。
     */
    @Test
    public void testConfirm() {
        // 定义 ConfirmCallBack().confirm(); 回调函数。
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             * 定义 confirmCallBack(); 回调函数。
             * 消息从 producer ---> exchange 会返回一个 confirmCallback。
             * @param correlationData 相关配置信息。
             * @param ack exchange 交换机是否成功收到消息。
             * @param cause 失败原因。
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("confirm(); 方法执行了。。。");
                if (ack) {
                    // 接收成功。
                    System.out.println("接收消息成功 ~ " + cause);// null。
                } else {
                    // 接收失败。
                    System.out.println("接收消息失败 ~ " + cause);
                }
            }
        });

        // 发送消息。
        rabbitTemplate.convertAndSend("test_exchange_confirm", "confirm", "confirmCallBack();.");
    }

    /**
     * 回退模式。
     * 当消息发送给 exchange 后，exchange 路由到 Queue 失败时会执行 ReturnCallback 的 returnedMessage();。
     * <p>
     * - 开启回退模式。
     * - 设置 ReturnCallback。<rabbit:connection-factory publisher-returns="true"/>
     * - 设置 Exchange 处理消息的模式。
     * - - 如果消息没有路由到 queue，则丢弃消息（默认）。
     * - - 如果消息没有路由到 queue，返回给消息发送方 ReturnCallback。
     */
    @Test
    public void testReturn() {
        // 设置交换机处理失败消息的模式。
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            /**
             *
             * @param message 消息对象。
             * @param replyCode 错误码。
             * @param replyText 错误信息。
             * @param exchange 交换机。
             * @param routingKey 路由 key。
             */
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("return 执行了。");
                System.out.println("message = " + message);
                System.out.println("replyCode = " + replyCode);
                System.out.println("replyText = " + replyText);
                System.out.println("exchange = " + exchange);
                System.out.println("routingKey = " + routingKey);
                /*
                return 执行了。
                message = (Body:'confirmCallBack();.' MessageProperties [headers={}, contentType=text/plain, contentEncoding=UTF-8, contentLength=0, receivedDeliveryMode=PERSISTENT, priority=0, deliveryTag=0])
                replyCode = 312
                replyText = NO_ROUTE
                exchange = test_exchange_confirm
                routingKey = confirm1
                 */
            }
        });

        // 发送消息。
        rabbitTemplate.convertAndSend("test_exchange_confirm", "confirm", "confirmCallBack();.");
    }

}
