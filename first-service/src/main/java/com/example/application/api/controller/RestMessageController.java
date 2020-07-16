package com.example.application.api.controller;

import com.example.application.domain.message.MessageManager;
import com.example.application.domain.model.message.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/rest")
public class RestMessageController {

    private final MessageManager messageManager;

    public RestMessageController(@Qualifier("rest") MessageManager messageManager) {
        this.messageManager = messageManager;
    }

    @GetMapping("/message/{id}")
    public Message getMessageByRest(@PathVariable Integer id) {
        log.info("Message from REST: " + messageManager.sendAndCollect(id).getMessage());
        return messageManager.sendAndCollect(id);
    }
}
