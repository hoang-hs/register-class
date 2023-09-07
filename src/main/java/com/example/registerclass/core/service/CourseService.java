package com.example.registerclass.core.service;

import com.example.registerclass.core.domain.Course;
import com.example.registerclass.core.domain.Inventory;
import com.example.registerclass.core.domain.Professor;
import com.example.registerclass.core.domain.repository.CourseRepository;
import com.example.registerclass.core.domain.repository.InventoryRepository;
import com.example.registerclass.core.domain.repository.ProfessorRepository;
import com.example.registerclass.exception.ResourceNotFoundException;
import com.example.registerclass.exception.SystemErrorException;
import com.example.registerclass.present.http.requests.CourseRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class CourseService {
    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;
    private final InventoryRepository inventoryRepository;

    public Course save(CourseRequest req) {
        Course course = req.ToDomain();
        Professor professor = professorRepository.findById(req.getProfessorId())
                .orElseThrow(() -> ResourceNotFoundException.WithMessage("professor not found"));

        course.setProfessor(professor);
        courseRepository.save(course);
        Inventory inventory = new Inventory();
        inventory.setCourse(course);
        inventory.setTotalInventory(req.getInventory());
        inventory.setTotalReserved(0);
        inventoryRepository.save(inventory);
        return course;
    }

    public Course update(CourseRequest req, Long id) {
        Course existedCourse = courseRepository.findById(id).orElseThrow(ResourceNotFoundException::Default);
        Course course = req.ToDomain();
        Professor professor = professorRepository.findById(req.getProfessorId())
                .orElseThrow(() -> ResourceNotFoundException.WithMessage("professor not found"));

        course.setProfessor(professor);
        course.setCreatedAt(existedCourse.getCreatedAt());
        course.setId(id);
        Optional<Inventory> optionalInventory = inventoryRepository.findByCourse(course);
        if (optionalInventory.isEmpty()) {
            log.error("inventory is empty, course :{}", course);
            throw SystemErrorException.Default();
        }
        Inventory inventory = optionalInventory.get();
        inventory.setTotalInventory(req.getInventory());
        inventoryRepository.save(inventory);
        return courseRepository.save(course);
    }

    public Course get(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::Default);
    }
}
