package com.example.registerclass.core.domain;

import com.example.registerclass.core.enums.StatusRegistration;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@ToString
@Table(name = "registration")
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private StatusRegistration status;

    @CreationTimestamp
    @JsonProperty("created_at")
    Timestamp createdAt;
    @UpdateTimestamp
    @JsonProperty("updated_at")
    Timestamp updatedAt;

    public Registration(Student student, Course course, StatusRegistration status) {
        this.student = student;
        this.course = course;
        this.status = status;
    }

    public Registration() {

    }
}
