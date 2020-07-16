package com.example.application.api.contoller;

import com.example.application.domain.message.MessageReceiver;
import com.example.application.domain.model.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("message")
public class MessageController {

    private final MessageReceiver messageReceiver;

    @GetMapping("/{id}")
    public Message provideMessage(@PathVariable Integer id) {
        return messageReceiver.receiveMessage(id);
    }
}
