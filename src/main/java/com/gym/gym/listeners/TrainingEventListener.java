package com.gym.gym.listeners;

import com.gym.gym.clients.Workload;
import com.gym.gym.entities.Training;
import com.gym.gym.senders.WorkloadSenderService;
import jakarta.persistence.PreRemove;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TrainingEventListener {

    private final WorkloadSenderService workloadSenderService;

    @PreRemove
    public void deleteTraining(Object entity) {
        if (entity instanceof Training existingTraining) {
            Workload workload = Workload.buildWorkload(existingTraining, "DELETE");
            workloadSenderService.sendMessage(workload);
        }
    }
}
