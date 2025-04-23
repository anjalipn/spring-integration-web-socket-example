package com.example.service;

import com.example.model.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class TaskStatusScheduler {

    private final TaskStatusService taskStatusService;
    private final SimpMessagingTemplate messagingTemplate;
    private final Random random = new Random();
    private final String[] states = {"ERROR", "EXECUTING", "SUCCESS"};
    private final String[] messages = {
        "Task failed due to error",
        "Processing data...",
        "Validating results...",
        "Task completed successfully",
        "Error in data processing",
        "Executing task step 2"
    };

    @Autowired
    public TaskStatusScheduler(TaskStatusService taskStatusService, SimpMessagingTemplate messagingTemplate) {
        this.taskStatusService = taskStatusService;
        this.messagingTemplate = messagingTemplate;
    }

    @Scheduled(fixedRate = 3000) // Update every 3 seconds
    public void updateTaskStatuses() {
        // Update status for tasks 1-5
        for (int i = 1; i <= 5; i++) {
            String taskId = String.valueOf(i);
            String state = states[random.nextInt(states.length)];
            String message = messages[random.nextInt(messages.length)];
            
            TaskStatus status = new TaskStatus(taskId, state, message);
            taskStatusService.updateTaskStatus(status);
            messagingTemplate.convertAndSend("/topic/task/" + taskId, status);
        }
    }
} 