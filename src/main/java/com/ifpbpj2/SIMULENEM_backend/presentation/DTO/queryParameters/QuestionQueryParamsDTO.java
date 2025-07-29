package com.ifpbpj2.SIMULENEM_backend.presentation.DTO.queryParameters;

import java.time.LocalDateTime;
import java.util.Set;

import com.ifpbpj2.SIMULENEM_backend.model.enums.Difficulty;
import com.ifpbpj2.SIMULENEM_backend.model.enums.QuestionType;

public record QuestionQueryParamsDTO(
                QuestionType questionType,
                String title,
                Set<String> categoryNames,
                Difficulty difficulty,
                LocalDateTime lastUsedDate) {

}