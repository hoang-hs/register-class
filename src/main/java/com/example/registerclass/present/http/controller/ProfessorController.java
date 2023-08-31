package com.example.registerclass.present.http.controller;

import com.example.registerclass.core.domain.Professor;
import com.example.registerclass.core.service.ProfessorService;
import com.example.registerclass.present.http.requests.ProfessorRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/professors")
public class ProfessorController {
    private final ProfessorService professorService;

    @Autowired
    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping("/{id}")
    Professor get(@PathVariable @Min(1) Long id) {
        return professorService.get(id);
    }

    @PostMapping("")
    Professor save(@RequestBody @Valid ProfessorRequest req) {
        return professorService.save(req);
    }

    @PutMapping("/{id}")
    Professor update(@RequestBody @Valid ProfessorRequest req, @PathVariable @Min(1) Long id) {
        return professorService.update(req, id);
    }
}
