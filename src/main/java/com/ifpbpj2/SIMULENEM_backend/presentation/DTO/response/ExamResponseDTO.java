package com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.hateoas.Link;

import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.Exam;


public record ExamResponseDTO(
    UUID id,
    String title,
    LocalDate applicationDate,
    boolean resultPublished,
    Double totalScore,
    Link sections
) {
    public ExamResponseDTO(Exam exam, Link secLink) {
       this(exam.getId(), exam.getTitle(), exam.getApplicationDate(), 
       exam.isResultPublished(), exam.getTotalScore(), secLink);
    }
    public ExamResponseDTO withSections(Link newSections) {
        return new ExamResponseDTO(
            this.id,
            this.title,
            this.applicationDate,
            this.resultPublished,
            this.totalScore,
            newSections
        );
    }
}
