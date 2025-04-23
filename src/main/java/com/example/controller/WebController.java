package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WebController {

    @GetMapping("/")
    public String root() {
        // Redirect to task 1 by default
        return "redirect:/1";
    }

    @GetMapping("/{taskId:[1-5]}")
    public String taskStatus(@PathVariable String taskId) {
        return "index";
    }
} 