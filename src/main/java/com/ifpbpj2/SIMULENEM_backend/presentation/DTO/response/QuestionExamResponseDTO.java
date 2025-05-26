package com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response;

import java.util.Set;
import java.util.UUID;

import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.QuestionExam;
import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Alternative;
import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Illustration;
import com.ifpbpj2.SIMULENEM_backend.model.enums.Difficulty;
import com.ifpbpj2.SIMULENEM_backend.model.enums.QuestionType;

public record QuestionExamResponseDTO(
        UUID id,
        Integer position,
        Double scoreQuestion,
        QuestionType questionType,
        String title,
        Illustration illustration,
        Set<Alternative> alternatives,
        Difficulty difficulty,
        String expectedAnswer
        ) {
        public QuestionExamResponseDTO(QuestionExam questionExam) {
            this(questionExam.getId(), questionExam.getPosition(), questionExam.getScoreQuestion(), questionExam.getQuestion().getQuestionType(),
                questionExam.getQuestion().getTitle(), questionExam.getQuestion().getIllustration(), 
                questionExam.getQuestion().getAlternatives(), questionExam.getQuestion().getDifficulty(),
                questionExam.getQuestion().getExpectedAnswer());
        }
}
