package com.lethabo.jobtracker.service;

import com.lethabo.jobtracker.model.Application;
import com.lethabo.jobtracker.repository.ApplicationRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public List<Application> getApplications() {
        return applicationRepository.findAll();
    }

    public Application createApplication(Application application) {
        return applicationRepository.save(application);
    }
}
