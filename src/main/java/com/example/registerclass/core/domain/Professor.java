package com.example.registerclass.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "professor")
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String address;

    @Column(unique = true)
    private String username;
    @JsonIgnore
    private String password;

    @CreationTimestamp
    @JsonProperty("created_at")
    Timestamp createdAt;
    @UpdateTimestamp
    @JsonProperty("updated_at")
    Timestamp updatedAt;

}
