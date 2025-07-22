package com.ifpbpj2.SIMULENEM_backend.presentation.controllers.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import com.ifpbpj2.SIMULENEM_backend.model.enums.Difficulty;
import com.ifpbpj2.SIMULENEM_backend.model.enums.QuestionType;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.PageableDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.QuestionRequestDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.QuestionResponseDTO;

public interface QuestionController {

    ResponseEntity<PageableDTO<QuestionResponseDTO>> findAll(
            @RequestParam(required = false) QuestionType questionType,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Set<String> categoryNames,
            @RequestParam(required = false) Difficulty difficulty,
            @RequestParam(required = false) LocalDateTime lastUsedDate,
            @ParameterObject Pageable pageable);

    ResponseEntity<QuestionResponseDTO> findById(UUID id);

    ResponseEntity<QuestionResponseDTO> save(QuestionRequestDTO obj);

    ResponseEntity<List<QuestionResponseDTO>> saveAll(List<QuestionRequestDTO> objList);

    ResponseEntity<QuestionResponseDTO> update(UUID id, QuestionRequestDTO obj);

    ResponseEntity<QuestionResponseDTO> deleteById(UUID id);
}
