package com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response;

import java.time.LocalDate;
import java.util.UUID;

import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.Exam;

public record ExamResponseDTO(
        UUID id,
        String title,
        LocalDate applicationDate,
        boolean resultPublished,
        Double totalScore) {
    public ExamResponseDTO(Exam exam) {
        this(exam.getId(), exam.getTitle(), exam.getApplicationDate(),
                exam.isResultPublished(), exam.getTotalScore());
    }

}
