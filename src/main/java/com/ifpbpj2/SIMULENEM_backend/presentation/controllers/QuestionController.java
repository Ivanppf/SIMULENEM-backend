package com.ifpbpj2.SIMULENEM_backend.presentation.controllers;

import com.ifpbpj2.SIMULENEM_backend.model.entities.Category;
import com.ifpbpj2.SIMULENEM_backend.model.enums.Difficulty;
import com.ifpbpj2.SIMULENEM_backend.model.enums.QuestionType;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.QuestionRequestDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.QuestionResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public interface QuestionController {
    
    @GetMapping
    ResponseEntity find(
            @RequestParam(value = "id", required = false) UUID id,
            @RequestParam(value = "questionType", required = false) QuestionType questionType,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "categories", required = false) Set<String> categories,
            @RequestParam(value = "difficulty", required = false) Difficulty difficulty,
            @RequestParam(value = "lastUsedDate", required = false) LocalDateTime lastUsedDate);

    @PostMapping
    ResponseEntity<QuestionResponseDTO> save(@RequestBody @Valid QuestionRequestDTO obj);

    @PutMapping("/{id}")
    ResponseEntity update(@PathVariable("id") UUID id, @Valid @RequestBody QuestionRequestDTO obj);

    @DeleteMapping("/{id}")
    ResponseEntity deleteById(@PathVariable("id") UUID id);
}
