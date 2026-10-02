package com.lethabo.jobtracker.controller;

import com.lethabo.jobtracker.model.Application;
import com.lethabo.jobtracker.model.ApplicationStatus;
import com.lethabo.jobtracker.service.ApplicationService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;
import jakarta.validation.Valid;

@RestController
public class ApplicationController {
    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping("/applications")
    public List<Application> getApplications(@RequestParam(required = false) ApplicationStatus status) {
        return applicationService.getApplications(status);
    }

    @GetMapping("/applications/{id}")
    public Application getApplication(@PathVariable Long id) {
        return applicationService.getApplication(id);
    }

    @PostMapping("/applications")
    public ResponseEntity<Application> createApplication(@RequestBody @Valid Application application) {
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
                                         @RequestBody @Valid Application application)
    {
        return applicationService.updateApplication(id, application);
    }
}