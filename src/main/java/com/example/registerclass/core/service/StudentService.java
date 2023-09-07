package com.example.registerclass.core.service;

import com.example.registerclass.core.domain.Student;
import com.example.registerclass.core.domain.repository.StudentRepository;
import com.example.registerclass.exception.ResourceNotFoundException;
import com.example.registerclass.present.http.requests.StudentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


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
        Student student = req.ToDomain();
        student.setPassword(password);
        return studentRepository.save(student);
    }

    public Student update(StudentRequest req, Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::Default);

        String password = encoder.encode(req.getPassword());
        Student s = req.ToDomain();
        s.setPassword(password);
        s.setCreatedAt(student.getCreatedAt());
        s.setId(id);
        return studentRepository.save(s);
    }

    public Student get(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::Default);
    }
}
