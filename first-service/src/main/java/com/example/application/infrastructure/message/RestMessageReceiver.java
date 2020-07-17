package com.example.application.infrastructure.message;

import com.example.application.domain.message.MessageManager;
import com.example.application.domain.model.message.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Qualifier("rest")
public class RestMessageReceiver implements MessageManager {

    private final RestTemplate restTemplate;

    @Value("${secondService.address}")
    private String serviceUrl;

    @Override
    public Message sendAndCollect(Integer id) {
        try {
            return restTemplate.getForObject(serviceUrl + id, Message.class);
        } catch (HttpStatusCodeException exception) {
            return new Message(id, exception.getResponseBodyAsString());
        }
    }
}
