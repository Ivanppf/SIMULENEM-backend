package com.ifpbpj2.SIMULENEM_backend.presentation.controllers.question;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.CategoryRequestDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.CategoryResponseDTO;


public interface CategoryController {
    ResponseEntity<List<CategoryResponseDTO>> find(String name);

    ResponseEntity<CategoryResponseDTO> findById(UUID id);

    ResponseEntity<CategoryResponseDTO> save(CategoryRequestDTO obj);

    ResponseEntity<CategoryResponseDTO> update(UUID id, CategoryRequestDTO obj);

    ResponseEntity<CategoryResponseDTO> deleteById(UUID id);
}
