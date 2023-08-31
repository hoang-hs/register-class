package com.example.registerclass.present.http.requests;

import com.example.registerclass.core.domain.Course;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CourseRequest {
    @NotBlank
    private String name;
    @NotBlank
    @JsonProperty("subject_type")
    String subjectType;
    String description;
    @NotNull
    @Min(1)
    @JsonProperty("professor_id")
    Long professorId;

    public Course ToDomain() {
        Course c = new Course();
        c.setName(this.getName());
        c.setDescription(this.getDescription());
        c.setSubjectType(this.getSubjectType());
        return c;
    }
}
