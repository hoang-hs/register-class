package com.example.registerclass.present.http.requests;

import com.example.registerclass.core.domain.Student;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class StudentRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    @Pattern(regexp = "(^$|[0-9]{10})")
    private String phoneNumber;
    @NotNull
    private Integer year;

    public Student ToDomain() {
        Student s = new Student();
        s.setUsername(this.getUsername());
        s.setName(this.getName());
        s.setEmail(this.getEmail());
        s.setPhoneNumber(this.getPhoneNumber());
        s.setYear(this.getYear());
        return s;
    }

}