package com.gym.gym.senders;

import com.gym.gym.clients.Workload;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkloadSenderService {

    private static final String WORKLOAD_QUEUE = "workload.queue";
    private final JmsTemplate jmsTemplate;

    public void sendMessage(Workload workload, String transactionId){

        jmsTemplate.convertAndSend(WORKLOAD_QUEUE, workload, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws JMSException {
                message.setStringProperty("Transaction-ID", transactionId);
                return message;
            }
        });
    }
}
