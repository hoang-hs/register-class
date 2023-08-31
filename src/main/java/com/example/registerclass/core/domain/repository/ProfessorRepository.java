package com.example.registerclass.core.domain.repository;

import com.example.registerclass.core.domain.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}
