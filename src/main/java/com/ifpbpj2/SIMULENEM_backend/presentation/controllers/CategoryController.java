package com.ifpbpj2.SIMULENEM_backend.presentation.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.CategoryRequestDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.CategoryResponseDTO;

import jakarta.validation.Valid;

public interface CategoryController {
    ResponseEntity<List<CategoryResponseDTO>> findById(UUID id, String name);

    ResponseEntity<CategoryResponseDTO> save(CategoryRequestDTO obj);

    ResponseEntity<CategoryResponseDTO> update(UUID id, CategoryRequestDTO obj);

    ResponseEntity<CategoryResponseDTO> deleteById(UUID id);
}
