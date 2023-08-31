package com.example.registerclass.core.service;

import com.example.registerclass.core.domain.Course;
import com.example.registerclass.core.domain.Inventory;
import com.example.registerclass.core.domain.Registration;
import com.example.registerclass.core.domain.Student;
import com.example.registerclass.core.domain.repository.CourseRepository;
import com.example.registerclass.core.domain.repository.InventoryRepository;
import com.example.registerclass.core.domain.repository.RegistrationRepository;
import com.example.registerclass.core.domain.repository.StudentRepository;
import com.example.registerclass.core.enums.StatusRegistration;
import com.example.registerclass.exception.ResourceNotFoundException;
import com.example.registerclass.exception.SystemErrorException;
import com.example.registerclass.present.http.requests.RegistrationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final KafkaService kafkaService;
    private final InventoryRepository inventoryRepository;

    @Autowired
    public RegistrationService(RegistrationRepository registrationRepository, StudentRepository studentRepository, CourseRepository courseRepository, KafkaService kafkaService, InventoryRepository inventoryRepository) {
        this.registrationRepository = registrationRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.kafkaService = kafkaService;
        this.inventoryRepository = inventoryRepository;
    }

    public Registration save(RegistrationRequest req) {
        Optional<Student> optionalStudent = studentRepository.findById(req.getStudentId());
        if (optionalStudent.isEmpty()) {
            throw ResourceNotFoundException.Default();
        }
        Optional<Course> optionalCourse = courseRepository.findById(req.getCourseId());
        if (optionalCourse.isEmpty()) {
            throw ResourceNotFoundException.Default();
        }
        Registration registration = new Registration(optionalStudent.get(), optionalCourse.get(), StatusRegistration.NEW);
        registrationRepository.save(registration);
        kafkaService.send("register-class", registration);
        return registration;
    }

    public boolean handleRegisterClass(Registration registration) {
        Optional<Inventory> optionalInventory = inventoryRepository.findByCourse(registration.getCourse());
        if (optionalInventory.isEmpty()) {
            log.error("inventory is empty, course :{}", registration.getCourse());
            return true;
        }
        Inventory inventory = optionalInventory.get();
        StatusRegistration status;
        if (inventory.getTotalReserved() > inventory.getTotalInventory()) {
            status = StatusRegistration.SUCCESS;
            inventory.setTotalReserved(inventory.getTotalReserved() + 1);
            inventoryRepository.save(inventory);
        } else {
            status = StatusRegistration.FAIL;
        }
        registration.setStatus(status);
        registrationRepository.save(registration);
        return true;
    }
}
