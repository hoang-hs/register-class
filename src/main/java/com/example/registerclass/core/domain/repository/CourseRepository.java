package com.example.registerclass.core.domain.repository;

import com.example.registerclass.core.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
