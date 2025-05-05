package com.ifpbpj2.SIMULENEM_backend.business.services;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.ifpbpj2.SIMULENEM_backend.model.entities.Category;
import com.ifpbpj2.SIMULENEM_backend.model.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> find(Category categoryFilter) {
        Example example = Example.of(categoryFilter,
                ExampleMatcher.matching()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return categoryRepository.findAll(example);

    }

    @Override
    public Category findById(UUID uuid) {
        existsById(uuid);
        return categoryRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Categoria com id " + uuid + " não encontrada"));
    }

    @Override
    public Set<Category> findByNameIn(Set<String> names) {
        Set<Category> categories = categoryRepository.findByNameIn(names).stream().collect(Collectors.toSet());
        if (categories.isEmpty())
            throw new RuntimeException("Não há nenhuma categoria com estes nomes");
        return categories;
    }

    @Override
    public Category save(Category category) {

        return categoryRepository.save(category);
    }

    @Override
    public Category update(UUID uuid, Category category) {
        existsById(uuid);
        Category oldCategory = findById(uuid);
        category.setId(uuid);
        category.setQuestions(oldCategory.getQuestions());
        return categoryRepository.save(category);
    }

    @Override
    public void deleteById(UUID uuid) {
        Category categories = findById(uuid);
        if (categories.getQuestions().isEmpty()) {
            categoryRepository.deleteById(uuid);
        } else {
            throw new RuntimeException("Ainda há questões cadastradas com essa categoria");
        }
    }

    @Override
    public void existsById(UUID uuid) {
        if (!categoryRepository.existsById(uuid)) {
            throw new RuntimeException("Categoria com id " + uuid + " não encontrada");
        }
    }

}
