package com.lethabo.jobtracker.controller;

import com.lethabo.jobtracker.model.ApplicationStatus;
import com.lethabo.jobtracker.model.Application;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController {

    @GetMapping("/applications")
    public Application getApplication() {
        return new Application(
                1L,
                "Microsoft",
                "Junior Software Developer",
                ApplicationStatus.APPLIED,
                "Cape Town",
                "Applied through the company website"
        );
    }
}