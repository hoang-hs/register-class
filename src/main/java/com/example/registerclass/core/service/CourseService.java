package com.example.registerclass.core.service;

import com.example.registerclass.core.domain.Course;
import com.example.registerclass.core.domain.Inventory;
import com.example.registerclass.core.domain.Professor;
import com.example.registerclass.core.domain.repository.CourseRepository;
import com.example.registerclass.core.domain.repository.InventoryRepository;
import com.example.registerclass.core.domain.repository.ProfessorRepository;
import com.example.registerclass.exception.ResourceNotFoundException;
import com.example.registerclass.present.http.requests.CourseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;
    private final InventoryRepository inventoryRepository;

    public Course save(CourseRequest req) {
        Course c = req.ToDomain();
        Optional<Professor> optionalProfessor = professorRepository.findById(req.getProfessorId());
        if (optionalProfessor.isEmpty()) {
            throw ResourceNotFoundException.WithMessage("professor not found");
        }
        c.setProfessor(optionalProfessor.get());
        Course course = courseRepository.save(c);
        // Todo pubsub
        Inventory inventory = new Inventory();
        inventory.setCourse(course);
        inventory.setTotalInventory(req.getInventory());
        inventory.setTotalReserved(0);
        inventoryRepository.save(inventory);
        return course;
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
