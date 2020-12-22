package com.geek.rabbitmq.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author geek
 */
@Component
public class OrderListener implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            // 接收转换消息。
            System.out.println(message);
            // 处理业务逻辑。
            System.out.println("处理业务逻辑。");
            System.out.println("根据订单 id 查询其状态。");
            System.out.println("判断状态是否为支付成功。");
            System.out.println("取消订单，回滚库存。");
            // 手动签收。（deliveryTag，签收多条消息）;。
            channel.basicAck(deliveryTag, true);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("出现异常，拒绝接收。");
            // 不重回队列，到死信队列中。
            // 拒绝签收。（deliveryTag，签收多条消息，requeue ~ 消息重回队列，broker 会重新发送该消息给消费端）;。
            channel.basicNack(deliveryTag, true, false);
//            channel.basicReject(deliveryTag, true);
        }
    }

    @Override
    public void onMessage(Message message) {

    }

    @Override
    public void onMessageBatch(List<Message> messages, Channel channel) {

    }

}
