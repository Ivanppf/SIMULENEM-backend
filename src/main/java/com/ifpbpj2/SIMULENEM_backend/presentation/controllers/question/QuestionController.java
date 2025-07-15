package com.ifpbpj2.SIMULENEM_backend.presentation.controllers.question;

import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.PageableDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.queryParameters.QuestionQueryParametersDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.QuestionRequestDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.QuestionResponseDTO;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.UUID;

public interface QuestionController {

    ResponseEntity<PageableDTO<QuestionResponseDTO>> findAll(@ModelAttribute QuestionQueryParametersDTO queryParameters,
            @ParameterObject Pageable pageable);

    ResponseEntity<QuestionResponseDTO> findById(UUID id);

    ResponseEntity<QuestionResponseDTO> save(QuestionRequestDTO obj);

    ResponseEntity<List<QuestionResponseDTO>> saveAll(List<QuestionRequestDTO> objList);

    ResponseEntity<QuestionResponseDTO> update(UUID id, QuestionRequestDTO obj);

    ResponseEntity<QuestionResponseDTO> deleteById(UUID id);
}
