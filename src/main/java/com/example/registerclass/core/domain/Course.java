package com.example.registerclass.core.domain;

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
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
    @JsonProperty("subject_type")
    private String subjectType;
    private String description;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @CreationTimestamp
    @JsonProperty("created_at")
    Timestamp createdAt;
    @UpdateTimestamp
    @JsonProperty("updated_at")
    Timestamp updatedAt;
}
