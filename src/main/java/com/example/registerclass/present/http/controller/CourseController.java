package com.example.registerclass.present.http.controller;

import com.example.registerclass.core.domain.Course;
import com.example.registerclass.core.service.CourseService;
import com.example.registerclass.present.http.requests.CourseRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/{id}")
    Course get(@PathVariable @Min(1) Long id) {
        return courseService.get(id);
    }

    @PostMapping("")
    Course save(@RequestBody @Valid CourseRequest req) {
        return courseService.save(req);
    }

    @PutMapping("/{id}")
    Course update(@RequestBody @Valid CourseRequest req, @PathVariable @Min(1) Long id) {
        return courseService.update(req, id);
    }

}
