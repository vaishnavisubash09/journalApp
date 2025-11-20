package com.engineeringdigest.journalApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {

    @GetMapping("/health-check")
    public String healthCheck(){
        return "hey dear user.... You might be crying out tears of joy after see that your app is running in the host!!You are making progress";
    }
}

