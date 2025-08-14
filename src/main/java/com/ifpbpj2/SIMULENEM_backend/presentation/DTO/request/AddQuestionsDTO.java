package com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;

public record AddQuestionsDTO(@NotEmpty Set<QuestionExamRequestDTO> questions) {

}
