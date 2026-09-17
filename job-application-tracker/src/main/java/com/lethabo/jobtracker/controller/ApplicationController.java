package com.lethabo.jobtracker.controller;

import com.lethabo.jobtracker.model.Application;
import com.lethabo.jobtracker.service.ApplicationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
public class ApplicationController {
    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping("/applications")
    public List<Application> getApplication() {
        return applicationService.getApplications();
    }

    @PostMapping("/applications")
    public ResponseEntity<Application> createApplication(@RequestBody Application application) {
        Application createdApplication = applicationService.createApplication(application);

        return ResponseEntity.status(201).body(createdApplication);
    }
}