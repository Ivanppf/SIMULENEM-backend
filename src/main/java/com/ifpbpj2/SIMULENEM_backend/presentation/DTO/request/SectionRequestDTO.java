package com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request;

import java.util.Set;

import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.QuestionExam;

public record SectionRequestDTO(
    Integer position,
    String title,
    Set<QuestionExam> questions
) {

}
