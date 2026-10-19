package com.lethabo.jobtracker;

import com.lethabo.jobtracker.model.Application;
import com.lethabo.jobtracker.model.ApplicationStatus;
import com.lethabo.jobtracker.repository.ApplicationRepository;
import com.lethabo.jobtracker.service.ApplicationService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTests {
    @Mock
    private ApplicationRepository applicationRepository;

    @InjectMocks
    private ApplicationService applicationService;

    @Test
    void getApplication_whenApplicationExists_returnsApplication() {
        Application application = new Application();
        application.setId(1L);

        when(applicationRepository.findById(1L))
                .thenReturn(Optional.of(application));

        Application result = applicationService.getApplication(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getApplication_whenApplicationDoesntExist_throwsException() {
        when(applicationRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> applicationService.getApplication(999L));
    }

    @Test
    void createApplication_returnsSavedApplication() {
        Application application = new Application();
        Application savedApplication = new Application();

        when(applicationRepository.save(application))
                .thenReturn(savedApplication);

        Application result = applicationService.createApplication(application);

        assertSame(result, savedApplication);
        verify(applicationRepository).save(application);
    }

    @Test
    void deleteApplication_whenApplicationExists_deletesApplication() {
        Application application = new Application();
        application.setId(1L);

        when(applicationRepository.findById(1L))
                .thenReturn(Optional.of(application));

        applicationService.deleteApplication(1L);
        verify(applicationRepository).deleteById(1L);
    }

    @Test
    void deleteApplication_whenApplicationDoesntExist_throwsException() {
        when(applicationRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> applicationService.deleteApplication(999L));
        verify(applicationRepository, never()).deleteById(999L);
    }

    @Test
    void updateApplication_whenApplicationExists_updatesApplication() {
        Application existingApplication = new Application();
        existingApplication.setId(1L);
        existingApplication.setCompany("Google");
        existingApplication.setPosition("Junior Developer");
        existingApplication.setStatus(ApplicationStatus.APPLIED);
        existingApplication.setLocation("Cape Town");
        existingApplication.setNotes("Applied through LinkedIn");

        Application updatedApplication = new Application();
        updatedApplication.setCompany("Microsoft");
        updatedApplication.setPosition("Backend Developer");
        updatedApplication.setStatus(ApplicationStatus.INTERVIEW);
        updatedApplication.setLocation("Johannesburg");
        updatedApplication.setNotes("Applied through Indeed");

        when(applicationRepository.findById(1L))
                .thenReturn(Optional.of(existingApplication));
        when(applicationRepository.save(existingApplication))
                .thenReturn(existingApplication);

        Application result = applicationService.updateApplication(1L, updatedApplication);

        assertSame(existingApplication, result);
        assertEquals(1L, result.getId());
        assertEquals("Microsoft", result.getCompany());
        assertEquals("Backend Developer", result.getPosition());
        assertEquals(ApplicationStatus.INTERVIEW, result.getStatus());
        assertEquals("Johannesburg", result.getLocation());
        assertEquals("Applied through Indeed", result.getNotes());

        verify(applicationRepository).save(existingApplication);
    }

    @Test
    void updateApplication_whenApplicationDoesntExist_throwsException() {
        Application updatedApplication = new Application();

        when(applicationRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> applicationService.updateApplication(999L, updatedApplication));
        verify(applicationRepository, never()).save(any(Application.class));
    }

    @Test
    void getApplications_whenStatusIsNull_returnsAllApplications() {
        Application application1 = new Application();
        Application application2 = new Application();
        List<Application> applications = List.of(application1, application2);

        when(applicationRepository.findAll())
                .thenReturn(applications);

        List<Application> result = applicationService.getApplications(null);

        assertSame(applications, result);
        assertEquals(2, result.size());
        verify(applicationRepository).findAll();
    }

    @Test
    void getApplications_whenStatusIsProvided_returnsMatchingApplications() {
        Application application1 = new Application();
        application1.setStatus(ApplicationStatus.APPLIED);
        Application application2 = new Application();
        application2.setStatus(ApplicationStatus.APPLIED);
        List<Application> matchingApplications = List.of(application1, application2);

        when(applicationRepository.findByStatus(ApplicationStatus.APPLIED))
                .thenReturn(matchingApplications);

        List<Application> result = applicationService.getApplications(ApplicationStatus.APPLIED);

        assertSame(matchingApplications, result);
        assertEquals(2, result.size());
        verify(applicationRepository).findByStatus(ApplicationStatus.APPLIED);
        verify(applicationRepository, never()).findAll();
    }
}