package com.example.application.api.contoller;

import com.example.application.domain.model.message.MessageNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(value = MessageNotFoundException.class)
    public ResponseEntity<Object> messageException(MessageNotFoundException exception) {
        log.warn(exception.getMessage());
        return new ResponseEntity<>("Message not found", HttpStatus.NOT_FOUND);
    }
}
