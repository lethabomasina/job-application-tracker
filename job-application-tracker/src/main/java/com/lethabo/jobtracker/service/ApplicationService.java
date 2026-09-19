package com.lethabo.jobtracker.service;

import com.lethabo.jobtracker.model.Application;
import com.lethabo.jobtracker.repository.ApplicationRepository;
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

    public Optional<Application> getApplication(Long id) {
        return applicationRepository.findById(id);
    }

    public Application createApplication(Application application) {
        return applicationRepository.save(application);
    }

    public boolean deleteApplication(Long id) {
        if (applicationRepository.findById(id).isPresent()) {
            applicationRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
