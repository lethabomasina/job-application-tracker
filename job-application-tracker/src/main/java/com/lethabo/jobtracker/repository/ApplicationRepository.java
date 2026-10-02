package com.lethabo.jobtracker.repository;

import com.lethabo.jobtracker.model.Application;
import com.lethabo.jobtracker.model.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByStatus(ApplicationStatus status);
}
