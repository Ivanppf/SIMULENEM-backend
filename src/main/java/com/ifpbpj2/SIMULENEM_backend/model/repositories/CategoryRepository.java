package com.ifpbpj2.SIMULENEM_backend.model.repositories;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifpbpj2.SIMULENEM_backend.model.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findByName(String name);

    List<Category> findByNameIn(Set<String> names);
}
