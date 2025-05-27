package com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response;

import java.util.Set;
import java.util.UUID;

import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Alternative;
import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Illustration;
import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Question;
import com.ifpbpj2.SIMULENEM_backend.model.enums.QuestionType;

public record QuestionResponseSumaryDTO(
        UUID questionId,
        QuestionType questionType,
        String title,
        Illustration illustration,
        Set<Alternative> alternatives,
        String expectedAnswer
) {
    public QuestionResponseSumaryDTO(Question question) {
        this(question.getId(), question.getQuestionType(), question.getTitle(), 
        question.getIllustration(), question.getAlternatives(), question.getExpectedAnswer());
    }
}
