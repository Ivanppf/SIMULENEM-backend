package com.ifpbpj2.SIMULENEM_backend.model.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifpbpj2.SIMULENEM_backend.model.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID>{}
