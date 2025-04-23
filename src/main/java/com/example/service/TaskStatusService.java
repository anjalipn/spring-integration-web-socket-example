package com.example.service;

import com.example.model.TaskStatus;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TaskStatusService {
    private final Map<String, TaskStatus> taskStatuses = new ConcurrentHashMap<>();

    public void updateTaskStatus(TaskStatus status) {
        taskStatuses.put(status.getTaskId(), status);
    }

    public TaskStatus getTaskStatus(String taskId) {
        return taskStatuses.get(taskId);
    }
} 