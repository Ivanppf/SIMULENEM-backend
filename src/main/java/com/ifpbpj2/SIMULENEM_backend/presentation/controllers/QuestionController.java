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

    ResponseEntity<QuestionResponseDTO> findById( UUID id)

    ResponseEntity<QuestionResponseDTO> save(QuestionRequestDTO obj);

    ResponseEntity<List<QuestionResponseDTO>> saveAll(List<QuestionRequestDTO> objList);

    ResponseEntity<QuestionResponseDTO> update(UUID id, QuestionRequestDTO obj);

    ResponseEntity<QuestionResponseDTO> deleteById(UUID id);
}
