package com.lethabo.jobtracker.service;

import com.lethabo.jobtracker.model.Application;
import com.lethabo.jobtracker.repository.ApplicationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public List<Application> getApplications() {
        return applicationRepository.findAll();
    }

    public Application getApplication(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Application not found"));
    }

    public Application createApplication(Application application) {
        return applicationRepository.save(application);
    }

    public void deleteApplication(Long id) {
        applicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Application not found"));

        applicationRepository.deleteById(id);
    }

    public Application updateApplication(Long id, Application application) {
        Application existingApplication = applicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Application not found"));

        existingApplication.setCompany(application.getCompany());
        existingApplication.setPosition(application.getPosition());
        existingApplication.setStatus(application.getStatus());
        existingApplication.setLocation(application.getLocation());
        existingApplication.setNotes(application.getNotes());

        return applicationRepository.save(existingApplication);
    }
}
