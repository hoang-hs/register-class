package com.example.registerclass.core.domain.repository;

import com.example.registerclass.core.domain.Course;
import com.example.registerclass.core.domain.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByCourse(Course course);

}
