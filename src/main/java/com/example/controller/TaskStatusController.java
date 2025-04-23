package com.example.controller;

import com.example.model.TaskStatus;
import com.example.service.TaskStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class TaskStatusController {

    private final TaskStatusService taskStatusService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public TaskStatusController(TaskStatusService taskStatusService, SimpMessagingTemplate messagingTemplate) {
        this.taskStatusService = taskStatusService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/update-status")
    public void updateTaskStatus(@Payload TaskStatus status) {
        taskStatusService.updateTaskStatus(status);
        messagingTemplate.convertAndSend("/topic/task/" + status.getTaskId(), status);
    }
} 