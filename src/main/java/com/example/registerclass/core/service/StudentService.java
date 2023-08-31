package com.example.registerclass.core.service;

import com.example.registerclass.core.domain.Student;
import com.example.registerclass.core.domain.repository.StudentRepository;
import com.example.registerclass.exception.ResourceNotFoundException;
import com.example.registerclass.present.http.requests.StudentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
        this.encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public Student save(StudentRequest req) {
        String password = encoder.encode(req.getPassword());
        Student s = req.ToDomain();
        s.setPassword(password);
        return studentRepository.save(s);
    }

    public Student update(StudentRequest req, Long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isEmpty()) {
            throw ResourceNotFoundException.Default();
        }
        String password = encoder.encode(req.getPassword());
        Student s = req.ToDomain();
        s.setPassword(password);
        s.setCreatedAt(optionalStudent.get().getCreatedAt());
        s.setId(id);
        return studentRepository.save(s);
    }

    public Student get(Long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isEmpty()) {
            throw ResourceNotFoundException.Default();
        }
        return optionalStudent.get();
    }
}
