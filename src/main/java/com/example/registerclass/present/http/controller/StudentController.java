package com.example.registerclass.present.http.controller;

import com.example.registerclass.core.service.StudentService;
import com.example.registerclass.core.domain.Student;
import com.example.registerclass.present.http.requests.StudentRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/{id}")
    Student get(@PathVariable @Min(1) Long id) {
        return studentService.get(id);
    }

    @PostMapping("")
    Student save(@RequestBody @Valid StudentRequest req) {
        return studentService.save(req);
    }

    @PutMapping("/{id}")
    Student update(@RequestBody @Valid StudentRequest req, @PathVariable @Min(1) Long id) {
        return studentService.update(req, id);
    }


}
