package com.example.registerclass.present.http.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegistrationRequest {
    @NotNull
    @Min(1)
    @JsonProperty("student_id")
    private Long studentId;

    @NotNull
    @Min(1)
    @JsonProperty("course_id")
    private Long courseId;
}
