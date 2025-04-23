package com.example.model;

public class TaskStatus {
    private String taskId;
    private String state;
    private String message;

    public TaskStatus() {
    }

    public TaskStatus(String taskId, String state, String message) {
        this.taskId = taskId;
        this.state = state;
        this.message = message;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
} 