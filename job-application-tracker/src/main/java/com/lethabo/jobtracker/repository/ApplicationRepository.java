package com.lethabo.jobtracker.repository;

import com.lethabo.jobtracker.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
