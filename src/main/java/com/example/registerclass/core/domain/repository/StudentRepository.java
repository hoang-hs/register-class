package com.example.registerclass.core.domain.repository;

import com.example.registerclass.core.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
