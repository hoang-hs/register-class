package com.example.registerclass.core.service;

import com.example.registerclass.core.domain.Course;
import com.example.registerclass.core.domain.Professor;
import com.example.registerclass.core.domain.repository.CourseRepository;
import com.example.registerclass.core.domain.repository.ProfessorRepository;
import com.example.registerclass.exception.ResourceNotFoundException;
import com.example.registerclass.present.http.requests.CourseRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, ProfessorRepository professorRepository) {
        this.courseRepository = courseRepository;
        this.professorRepository = professorRepository;
    }

    public Course save(CourseRequest req) {
        Course c = req.ToDomain();
        Optional<Professor> optionalProfessor = professorRepository.findById(req.getProfessorId());
        if (optionalProfessor.isEmpty()) {
            throw ResourceNotFoundException.WithMessage("professor not found");
        }
        c.setProfessor(optionalProfessor.get());
        return courseRepository.save(c);
    }

    public Course update(CourseRequest req, Long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isEmpty()) {
            throw ResourceNotFoundException.Default();
        }
        Course c = req.ToDomain();
        Optional<Professor> optionalProfessor = professorRepository.findById(req.getProfessorId());
        if (optionalProfessor.isEmpty()) {
            throw ResourceNotFoundException.WithMessage("professor not found");
        }
        c.setProfessor(optionalProfessor.get());
        c.setCreatedAt(optionalCourse.get().getCreatedAt());
        c.setId(id);
        return courseRepository.save(c);
    }

    public Course get(Long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isEmpty()) {
            throw ResourceNotFoundException.Default();
        }
        return optionalCourse.get();
    }
}
