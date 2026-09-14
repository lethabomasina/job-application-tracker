package com.lethabo.jobtracker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController {

    @GetMapping("/applications")
    public String getApplications() {
        return "Here are my job applications";
    }
}