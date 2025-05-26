package com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response;

import java.util.List;
import java.util.UUID;

import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.Section;

public record SectionResponseDTO(
    UUID id,
    Integer position,
    String title,
    List<QuestionExamResponseDTO> questionExams 
) {
    public SectionResponseDTO(Section section, List<QuestionExamResponseDTO> questionExams) {
        this(section.getId(), section.getPosition(), section.getTitle(), questionExams);
    }

}
