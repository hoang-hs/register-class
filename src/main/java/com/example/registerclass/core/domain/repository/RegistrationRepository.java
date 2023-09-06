package com.example.registerclass.core.domain.repository;

import com.example.registerclass.core.domain.Course;
import com.example.registerclass.core.domain.Registration;
import com.example.registerclass.core.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    Optional<Registration> findByCourseAndStudent(Course course, Student student);
}
