package com.gym.gym.clients;

import com.gym.gym.config.MyFeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "GYM-WORKLOAD-SERVICE",
             configuration = MyFeignClientConfig.class,
             fallback = WorkloadServiceClientFallback.class)
public interface WorkloadServiceClient {

    @PostMapping("/workloads")
    ResponseEntity<HttpStatus> updateWorkload(@RequestBody Workload workload,
                                              @RequestHeader("Transaction-ID") String transactionId,
                                              @RequestHeader("Authorization") String jwtToken);
}
