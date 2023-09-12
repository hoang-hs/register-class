package com.example.registerclass.core.domain.repository;

import com.example.registerclass.core.domain.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Optional<Professor> findByUsername(String username);
}
