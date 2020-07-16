package com.example.application.api.kafka;

import com.example.application.domain.message.MessageReceiver;
import com.example.application.domain.model.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaReplier {

    private final MessageReceiver messageReceiver;

    @KafkaListener(topics = "${kafka.reuest.topic}", groupId = "${kafka.group.id}")
    @SendTo
    public Message kafkaListener(Integer id) {
        return messageReceiver.receiveMessage(id);
    }
}