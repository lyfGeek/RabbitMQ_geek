package com.geek.rabbitmq.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Consumer 限流机制。
 * - 确保 ack 机制为手动确认。
 * - listener-container 属性 prefetch = 1
 * ~ 表示消费端每次从 mq 拉取一条消息来消费，直到手动确认消费完毕后，才会去拉取下一条消息。
 *
 * @author geek
 */
@Component
public class QosListener implements ChannelAwareMessageListener {

    /**
     * Callback for processing a received Rabbit message.
     * <p>Implementors are supposed to process the given Message,
     * typically sending reply messages through the given Session.
     *
     * @param message the received AMQP message (never <code>null</code>)
     * @param channel the underlying Rabbit Channel (never <code>null</code>)
     * @throws Exception Any.
     */
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        Thread.sleep(1000);
        // 获取消息。
        System.out.println(new String(message.getBody()));
        // 业务处理逻辑。
        // 签收。
//        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
    }

    @Override
    public void onMessage(Message message) {

    }

    @Override
    public void onMessageBatch(List<Message> messages, Channel channel) {

    }

}
