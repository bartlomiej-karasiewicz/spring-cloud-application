package com.example.application.configuration;

import com.example.application.domain.model.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

@Configuration
@RefreshScope
public class KafkaConfig {

    @Value("${kafka.group.id}")
    private String groupId;

    @Value("${kafka.reply.topic}")
    private String replyTopic;

    @Bean
    public ReplyingKafkaTemplate<String, Integer, Message> replyingKafkaTemplate(ProducerFactory<String, Integer> producerFactory,
                                                                                 ConcurrentKafkaListenerContainerFactory<String, Message> factory) {
        ConcurrentMessageListenerContainer<String, Message> replyContainer = factory.createContainer(replyTopic);
        replyContainer.getContainerProperties().setMissingTopicsFatal(false);
        replyContainer.getContainerProperties().setGroupId(groupId);
        return new ReplyingKafkaTemplate<>(producerFactory, replyContainer);
    }

    @Bean
    public KafkaTemplate<String, Message> replyTemplate(ProducerFactory<String, Message> producerFactory,
                                                        ConcurrentKafkaListenerContainerFactory<String, Message> factory) {
        KafkaTemplate<String, Message> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        factory.getContainerProperties().setMissingTopicsFatal(false);
        factory.setReplyTemplate(kafkaTemplate);
        return kafkaTemplate;
    }
}