package com.example.registerclass.present.http.requests;

import com.example.registerclass.core.domain.Course;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CourseRequest {
    @NotBlank
    private String name;
    @NotBlank
    String subjectType;
    String description;
    @NotNull
    @Min(1)
    Long professorId;

    @NotNull
    @Min(1)
    Integer inventory;

    public Course ToDomain() {
        Course c = new Course();
        c.setName(this.getName());
        c.setDescription(this.getDescription());
        c.setSubjectType(this.getSubjectType());
        return c;
    }
}
