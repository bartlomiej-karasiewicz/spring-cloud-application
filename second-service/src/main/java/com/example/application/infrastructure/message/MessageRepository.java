package com.example.application.infrastructure.message;

import com.example.application.domain.model.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Integer> {

}
