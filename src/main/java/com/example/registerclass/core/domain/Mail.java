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
@Table(name = "mail")
public class Mail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "registration_id")
    private Registration registration;

    @CreationTimestamp
    @JsonProperty("created_at")
    Timestamp createdAt;
    @UpdateTimestamp
    @JsonProperty("updated_at")
    Timestamp updatedAt;
}
