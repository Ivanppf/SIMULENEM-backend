package com.ifpbpj2.SIMULENEM_backend.presentation.controllers;

import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Question;
import com.ifpbpj2.SIMULENEM_backend.model.enums.Difficulty;
import com.ifpbpj2.SIMULENEM_backend.model.enums.QuestionType;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.QuestionRequestDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.QuestionResponseDTO;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface QuestionController {

    ResponseEntity<Page<QuestionResponseDTO>> find(
            Pageable pageable,
            UUID id,
            QuestionType questionType,
            String title,
            Set<String> categories,
            Difficulty difficulty,
            LocalDateTime lastUsedDate);

    ResponseEntity<QuestionResponseDTO> save(@RequestBody @Valid QuestionRequestDTO obj);

    ResponseEntity<List<QuestionResponseDTO>> saveAll(@RequestBody @Valid List<QuestionRequestDTO> objList);

    ResponseEntity<QuestionResponseDTO> update(@PathVariable("id") UUID id, @Valid @RequestBody QuestionRequestDTO obj);

    ResponseEntity<QuestionResponseDTO> deleteById(@PathVariable("id") UUID id);
}
