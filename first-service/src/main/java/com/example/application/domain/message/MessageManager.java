package com.example.application.domain.message;

import com.example.application.domain.model.message.Message;

public interface MessageManager {
    Message sendAndCollect(Integer id);
}
