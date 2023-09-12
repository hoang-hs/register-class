package com.example.registerclass.core.service;

import com.example.registerclass.core.domain.Professor;
import com.example.registerclass.core.domain.Student;
import com.example.registerclass.core.domain.repository.ProfessorRepository;
import com.example.registerclass.core.domain.repository.StudentRepository;
import com.example.registerclass.core.enums.Role;
import com.example.registerclass.exception.BadRequestException;
import com.example.registerclass.exception.ResourceNotFoundException;
import com.example.registerclass.exception.SystemErrorException;
import com.example.registerclass.present.http.requests.LoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthService {

    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;

    public void login(LoginRequest req) {
        if (EnumUtils.isValidEnum(Role.class, req.getRole())) {
            throw BadRequestException.WithMessage("role invalid");
        }
        Role role = Role.valueOf(req.getRole());
        String username = "";
        switch (role) {
            case STUDENT:
                Student student = studentRepository.findByUsername(req.getUsername())
                        .orElseThrow(ResourceNotFoundException::Default);
                username = student.getUsername();
            case PROFESSOR:
                Professor professor = professorRepository.findByUsername(req.getUsername())
                        .orElseThrow(ResourceNotFoundException::Default);
                username = professor.getUsername();
            default:
                throw SystemErrorException.Default();
        }
    }

}
