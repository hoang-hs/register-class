package com.example.registerclass.core.domain.repository;

import com.example.registerclass.core.domain.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
}
