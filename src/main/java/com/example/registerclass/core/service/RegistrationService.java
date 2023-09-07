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
import com.example.registerclass.exception.BadRequestException;
import com.example.registerclass.exception.ResourceNotFoundException;
import com.example.registerclass.exception.SystemErrorException;
import com.example.registerclass.present.http.requests.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final KafkaService kafkaService;
    private final InventoryRepository inventoryRepository;

    public Registration save(RegistrationRequest req) {
        //Todo cache
        Optional<Student> optionalStudent = studentRepository.findById(req.getStudentId());
        if (optionalStudent.isEmpty()) {
            throw ResourceNotFoundException.Default();
        }
        Student student = optionalStudent.get();
        Optional<Course> optionalCourse = courseRepository.findById(req.getCourseId());
        if (optionalCourse.isEmpty()) {
            throw ResourceNotFoundException.Default();
        }
        Course course = optionalCourse.get();
        Optional<Inventory> optionalInventory = inventoryRepository.findByCourse(course);
        if (optionalInventory.isEmpty()) {
            log.error("inventory is empty, course :{}", course);
            throw SystemErrorException.Default();
        }
        Inventory inventory = optionalInventory.get();
        if (!inventory.isAvailable()) {
            throw BadRequestException.WithMessage("het slot");
        }

        Registration registration;
        Optional<Registration> optionalRegistration = registrationRepository.findByCourseAndStudent(course, student);
        if (optionalRegistration.isPresent()) {
            registration = optionalRegistration.get();
            if (registration.getStatus() == StatusRegistration.NEW || registration.getStatus() == StatusRegistration.SUCCESS) {
                throw BadRequestException.WithMessage("data exist");
            }
        } else {
            registration = new Registration(student, course);
        }
        registration.setStatus(StatusRegistration.NEW);
        registrationRepository.save(registration);
        kafkaService.send("register-class", registration);
        return registration;
    }

    public Registration cancel(Long id) {
        Optional<Registration> optionalRegistration = registrationRepository.findById(id);
        if (optionalRegistration.isEmpty()) {
            throw ResourceNotFoundException.Default();
        }
        Registration registration = optionalRegistration.get();
        if (registration.getStatus() != StatusRegistration.SUCCESS) {
            throw BadRequestException.WithMessage("registration not success");
        }
        registration.setStatus(StatusRegistration.CANCEL);
        registrationRepository.save(registration);
        kafkaService.send("cancel-class", registration);
        return registration;
    }

}
