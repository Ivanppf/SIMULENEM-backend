package com.ifpbpj2.SIMULENEM_backend.presentation.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.SessionScope;

import com.ifpbpj2.SIMULENEM_backend.business.services.CategoryService;
import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Category;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.CategoryRequestDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.CategoryResponseDTO;

import jakarta.validation.Valid;

@RestController
@SessionScope
@RequestMapping("/categories")
public class CategoryControllerImpl implements CategoryController {

    private final CategoryService categoryService;

    public CategoryControllerImpl(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> findById(
            @RequestParam(value = "id", required = false) UUID id,
            @RequestParam(value = "name", required = false) String name) {
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        List<Category> categories = categoryService.find(category);
        return ResponseEntity.ok().body(categories.stream().map(CategoryResponseDTO::new).toList());

    }

    @Override
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> save(@RequestBody CategoryRequestDTO obj) {
        Category category = new Category(obj);
        category = categoryService.save(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CategoryResponseDTO(category));
    }

    @Override
    @PutMapping("{id}")
    public ResponseEntity<CategoryResponseDTO> update(@PathVariable("id") UUID id,
            @Valid @RequestBody CategoryRequestDTO obj) {
        Category category = new Category(obj);
        category = categoryService.update(id, category);
        return ResponseEntity.ok().body(new CategoryResponseDTO(category));
    }

    @Override
    @DeleteMapping("{id}")
    public ResponseEntity<CategoryResponseDTO> deleteById(@PathVariable("id") UUID id) {
        categoryService.deleteById(id);
        return new ResponseEntity<>((HttpStatus.NO_CONTENT));
    }

}
