package com.gym.gym.sender;

import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Sender {

    private final JmsTemplate jmsTemplate;

    public void sendMessage(String destination, String message){
        jmsTemplate.convertAndSend(destination, message);
    }
}
