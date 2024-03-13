package com.gym.gym.clients;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Component
public class WorkloadServiceClientFallback implements WorkloadServiceClient{
    @Override
    public ResponseEntity<HttpStatus> updateWorkload(@RequestBody Workload workload,
                                                     @RequestHeader String transactionId,
                                                     @RequestHeader String jwtToken) {
        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }
}
