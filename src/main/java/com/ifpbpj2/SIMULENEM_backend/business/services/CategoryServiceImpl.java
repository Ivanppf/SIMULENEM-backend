package com.ifpbpj2.SIMULENEM_backend.business.services;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.ifpbpj2.SIMULENEM_backend.business.services.exceptions.CategoryNotFoundException;
import com.ifpbpj2.SIMULENEM_backend.business.services.exceptions.EntityInUseException;
import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Category;
import com.ifpbpj2.SIMULENEM_backend.model.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category findByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new CategoryNotFoundException("Categoria com o nome " + name + " não foi encontrada" ));
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
        return categoryRepository.findById(uuid)
                .orElseThrow(() -> new CategoryNotFoundException(uuid));
    }

    @Override
    public Set<Category> findByNameIn(Set<String> names) {
        Set<Category> categories = categoryRepository.findByNameIn(names).stream().collect(Collectors.toSet());
        if (categories.isEmpty())
            throw new CategoryNotFoundException("Não há nenhuma categoria com estes nomes");
        return categories;
    }

    @Override
    public Category save(Category category) {

        return categoryRepository.save(category);
    }

    @Override
    public Category update(UUID uuid, Category category) {
        Category oldCategory = findById(uuid);
        category.setId(uuid);
        category.setQuestions(oldCategory.getQuestions());
        return categoryRepository.save(category);
    }

    @Override
    public void deleteById(UUID uuid) {
        Category category = findById(uuid);
        if (category.getQuestions().isEmpty()) {
            categoryRepository.delete(category);
        } else {
            throw new EntityInUseException("Ainda há questões cadastradas com essa categoria");
        }
    }

    @Override
    public void existsById(UUID uuid) {
        if (!categoryRepository.existsById(uuid)) {
            throw new CategoryNotFoundException(uuid);
        }
    }

}
