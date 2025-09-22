package org.example.queuepractice.controller;

import lombok.RequiredArgsConstructor;
import org.example.queuepractice.common.dto.MessageDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MessageController {

    private final JmsTemplate jmsTemplate;

    @Value("${spring.activemq.destination}")
    private String destination;

    @PostMapping("")
    public void sendMessage(@RequestBody MessageDto messageDto) {
        jmsTemplate.convertAndSend(destination, messageDto);
    }
}
