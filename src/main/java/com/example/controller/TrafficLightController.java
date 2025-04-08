package com.example.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class TrafficLightController {

    @MessageMapping("/signal")
    @SendTo("/topic/traffic-light")
    public String sendSignal() {
        return "GREEN"; // This will be replaced by the scheduled task
    }
} 