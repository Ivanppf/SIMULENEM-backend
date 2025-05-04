package com.ifpbpj2.SIMULENEM_backend.business.services;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.ifpbpj2.SIMULENEM_backend.model.entities.Category;

public interface CategoryService {

    List<Category> find(Category categoryFilter);

    Set<Category> findByNameIn(Set<String> names);

    Category findById(UUID uuid);

    Category save(Category category);

    Category update(UUID uuid, Category category);

    void deleteById(UUID uuid);

    void existsById(UUID uuid);
}
