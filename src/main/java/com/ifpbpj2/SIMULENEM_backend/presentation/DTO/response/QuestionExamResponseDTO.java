package com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response;

import java.util.UUID;

import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.QuestionExam;

public record QuestionExamResponseDTO(
        UUID id,
        Integer position,
        Double scoreQuestion,
        QuestionResponseSumaryDTO question
        ) {
        public QuestionExamResponseDTO(QuestionExam questionExam) {
            this(questionExam.getId(), questionExam.getPosition(), questionExam.getScoreQuestion(), 
            new QuestionResponseSumaryDTO(questionExam.getQuestion()));
        }
}
