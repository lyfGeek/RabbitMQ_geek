package com.geek.rabbitmq.listener;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpringQueueListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        System.out.println("message = " + message);
    }

    @Override
    public void containerAckMode(AcknowledgeMode mode) {

    }

    @Override
    public void onMessageBatch(List<Message> messages) {

    }

}
