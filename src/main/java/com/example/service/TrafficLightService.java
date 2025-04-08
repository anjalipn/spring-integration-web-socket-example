package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TrafficLightService {

    private final SimpMessagingTemplate messagingTemplate;
    private String currentSignal = "RED";

    @Autowired
    public TrafficLightService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Scheduled(fixedRate = 3000) // Send signal every 3 seconds
    public void sendTrafficLightSignal() {
        switch (currentSignal) {
            case "RED":
                currentSignal = "GREEN";
                break;
            case "GREEN":
                currentSignal = "AMBER";
                break;
            case "AMBER":
                currentSignal = "RED";
                break;
        }
        messagingTemplate.convertAndSend("/topic/traffic-light", currentSignal);
    }
} 