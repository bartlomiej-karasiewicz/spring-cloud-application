package com.example.application.infrastructure.message;

import com.example.application.domain.message.MessageManager;
import com.example.application.domain.model.message.Message;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Qualifier("kafka")
public class KafkaMessageReceiver implements MessageManager {

    @Value("${kafka.reuest.topic}")
    private String requestTopic;
    private final ReplyingKafkaTemplate<String, Integer, Message> replyingKafkaTemplate;

    @Override
    public Message sendAndCollect(Integer id) {
        ProducerRecord<String, Integer> record = new ProducerRecord<>(requestTopic, id);
        RequestReplyFuture<String, Integer, Message> replyFuture = replyingKafkaTemplate.sendAndReceive(record);
        ConsumerRecord<String, Message> response = null;
        try {
            response = replyFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return response.value();
    }
}
