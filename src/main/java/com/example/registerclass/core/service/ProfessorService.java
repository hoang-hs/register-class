package com.example.registerclass.core.service;

import com.example.registerclass.core.domain.Professor;
import com.example.registerclass.core.domain.repository.ProfessorRepository;
import com.example.registerclass.exception.ResourceNotFoundException;
import com.example.registerclass.present.http.requests.ProfessorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfessorService {
    private final ProfessorRepository professorRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
        this.encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    public Professor save(ProfessorRequest req) {
        String password = encoder.encode(req.getPassword());
        Professor s = req.ToDomain();
        s.setPassword(password);
        return professorRepository.save(s);
    }

    public Professor update(ProfessorRequest req, Long id) {
        Professor professor = professorRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::Default);

        String password = encoder.encode(req.getPassword());
        Professor s = req.ToDomain();
        s.setPassword(password);
        s.setCreatedAt(professor.getCreatedAt());
        s.setId(id);
        return professorRepository.save(s);
    }

    public Professor get(Long id) {
        return professorRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::Default);
    }
}
