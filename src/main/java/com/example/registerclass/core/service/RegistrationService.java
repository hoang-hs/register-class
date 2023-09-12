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
import com.example.registerclass.present.http.requests.GetRegistrationRequest;
import com.example.registerclass.present.http.requests.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
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
        Student student = studentRepository.findById(req.getStudentId())
                .orElseThrow(ResourceNotFoundException::Default);

        Course course = courseRepository.findById(req.getCourseId())
                .orElseThrow(ResourceNotFoundException::Default);

        Optional<Inventory> optionalInventory = inventoryRepository.findByCourse(course);
        if (optionalInventory.isEmpty()) {
            log.error("inventory is empty, course :{}", course);
            throw SystemErrorException.Default();
        }
        Inventory inventory = optionalInventory.get();
        if (!inventory.isAvailable()) {
            throw BadRequestException.WithMessage("out of slot");
        }

        Registration registration;
        Optional<Registration> optionalRegistration = registrationRepository.findByCourseAndStudent(course, student);
        if (optionalRegistration.isPresent()) {
            registration = optionalRegistration.get();
            if (registration.getStatus() == StatusRegistration.NEW || registration.getStatus() == StatusRegistration.SUCCESS) {
                throw BadRequestException.WithMessage("data existed");
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
        Registration registration = registrationRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::Default);

        if (registration.getStatus() != StatusRegistration.SUCCESS) {
            throw BadRequestException.WithMessage("registration not success");
        }
        registration.setStatus(StatusRegistration.CANCEL);
        registrationRepository.save(registration);
        kafkaService.send("cancel-class", registration);
        return registration;
    }

    public List<Registration> get(GetRegistrationRequest req) {
        if (!EnumUtils.isValidEnum(StatusRegistration.class, req.getStatus())) {
            log.error("status invalid, req:{}", req);
            throw SystemErrorException.Default();
        }
        Pageable pageable = req.getPageRequest().buildPageable();
        return registrationRepository.findAllByStudent_IdAndStatus(pageable, req.getStudentId(), StatusRegistration.valueOf(req.getStatus()));
    }

}
