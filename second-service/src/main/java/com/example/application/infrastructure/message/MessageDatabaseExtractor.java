package com.example.application.infrastructure.message;

import com.example.application.domain.message.MessageReceiver;
import com.example.application.domain.model.message.Message;
import com.example.application.domain.model.message.MessageNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageDatabaseExtractor implements MessageReceiver {

    private final MessageRepository messageRepository;

    @Override
    public Message receiveMessage(Integer id) {
        return messageRepository.findById(id).orElseThrow(MessageNotFoundException::new);
    }
}
