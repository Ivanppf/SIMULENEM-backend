package com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.ifpbpj2.SIMULENEM_backend.model.entities.Alternative;
import com.ifpbpj2.SIMULENEM_backend.model.entities.Illustration;
import com.ifpbpj2.SIMULENEM_backend.model.entities.Question;
import com.ifpbpj2.SIMULENEM_backend.model.enums.Difficulty;
import com.ifpbpj2.SIMULENEM_backend.model.enums.QuestionType;

public record QuestionResponseDTO(
        UUID id,
        QuestionType questionType,
        String title,
        Illustration illustration,
        Set<Alternative> alternatives,
        Set<CategoryResponseDTO> categories,
        Difficulty difficulty,
        String expectedAnswer,
        LocalDateTime lastUsedDate) {
    public QuestionResponseDTO(Question question) {
        this(question.getId(), question.getQuestionType(), question.getTitle(), question.getIllustration(),
                question.getAlternatives(),
                question.getCategories().stream().map(CategoryResponseDTO::new).collect(Collectors.toSet()),
                question.getDifficulty(),
                question.getExpectedAnswer(), question.getLastUsedDate());
    }
}
