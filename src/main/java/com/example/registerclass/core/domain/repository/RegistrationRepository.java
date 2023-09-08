package com.example.registerclass.core.domain.repository;

import com.example.registerclass.core.domain.Course;
import com.example.registerclass.core.domain.Registration;
import com.example.registerclass.core.domain.Student;
import com.example.registerclass.core.enums.StatusRegistration;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    Optional<Registration> findByCourseAndStudent(Course course, Student student);

    List<Registration> findAllByStudentAndStatus(Student student, StatusRegistration status);

    List<Registration> findAllByStudent_IdAndStatus(Pageable pageable, Long id, StatusRegistration status);
}
