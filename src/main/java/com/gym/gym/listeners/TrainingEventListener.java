package com.gym.gym.listeners;

import com.gym.gym.clients.Workload;
import com.gym.gym.clients.WorkloadServiceClient;
import com.gym.gym.entities.Training;
import jakarta.persistence.PreRemove;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;

@RequiredArgsConstructor
public class TrainingEventListener {

    private final WorkloadServiceClient workloadServiceClient;

    @PreRemove
    public void deleteTraining(Object entity) {
        if (entity instanceof Training existingTraining) {
            Workload workload = Workload.buildWorkload(existingTraining, "DELETE");
            workloadServiceClient.updateWorkload(workload, MDC.get("Transaction-ID"), MDC.get("Authorization"));
        }
    }
}
