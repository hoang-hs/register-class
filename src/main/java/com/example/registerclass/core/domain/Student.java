package com.example.registerclass.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.sql.Timestamp;
import java.util.Objects;


@Getter
@Setter
@ToString
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    @JsonIgnore
    private String password;

    private String name;
    private String email;
    @JsonProperty("phone_number")
    private String phoneNumber;
    private int year;
    @CreationTimestamp
    @JsonProperty("created_at")
    Timestamp createdAt;
    @UpdateTimestamp
    @JsonProperty("updated_at")
    Timestamp updatedAt;


    public Student(String name, String email, String phoneNumber, int year) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.year = year;
    }


    public Student() {

    }

}
