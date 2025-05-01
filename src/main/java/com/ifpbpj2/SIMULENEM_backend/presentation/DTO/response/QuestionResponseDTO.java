package com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

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
        Difficulty difficulty,
        Integer estimatedTimeInMin,
        String expectedAnswer,
        LocalDateTime lastUsedDate) {
    public QuestionResponseDTO(Question question) {
        this(question.getId(), question.getQuestionType(), question.getTitle(), question.getIllustration(),
                question.getAlternatives(), question.getDifficulty(), question.getEstimatedTimeInMin(),
                question.getExpectedAnswer(), question.getLastUsedDate());
    }
}
