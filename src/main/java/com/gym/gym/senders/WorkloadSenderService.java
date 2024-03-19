package com.gym.gym.senders;

import com.gym.gym.clients.Workload;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkloadSenderService {

    private static final String WORKLOAD_QUEUE = "workload.queue";
    private final JmsTemplate jmsTemplate;

    public void sendMessage(Workload workload){
        jmsTemplate.convertAndSend(WORKLOAD_QUEUE, workload);
    }
}
