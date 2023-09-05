package com.example.registerclass.present.http.requests;

import com.example.registerclass.core.domain.Professor;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProfessorRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String address;
    @NotBlank
    @Pattern(regexp = "(^$|[0-9]{10})")
    private String phoneNumber;

    public Professor ToDomain() {
        Professor s = new Professor();
        s.setUsername(this.getUsername());
        s.setName(this.getName());
        s.setEmail(this.getEmail());
        s.setPhoneNumber(this.getPhoneNumber());
        s.setAddress(this.getAddress());
        return s;
    }

}
