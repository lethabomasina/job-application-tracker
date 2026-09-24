package com.lethabo.jobtracker.controller;

import com.lethabo.jobtracker.model.Application;
import com.lethabo.jobtracker.service.ApplicationService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;

@RestController
public class ApplicationController {
    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping("/applications")
    public List<Application> getApplications() {
        return applicationService.getApplications();
    }

    @GetMapping("/applications/{id}")
    public Application getApplication(@PathVariable Long id) {
        return applicationService.getApplication(id);
    }

    @PostMapping("/applications")
    public ResponseEntity<Application> createApplication(@RequestBody Application application) {
        Application createdApplication = applicationService.createApplication(application);

        return ResponseEntity.status(201).body(createdApplication);
    }

    @DeleteMapping("/applications/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/applications/{id}")
    public Application updateApplication(@PathVariable Long id,
                                                         @RequestBody Application application)
    {
        return applicationService.updateApplication(id, application);
    }
}