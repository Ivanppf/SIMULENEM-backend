package com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request;

import java.time.LocalDate;
import java.util.Set;

import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.Section;

public record ExamRequestDTO(
    LocalDate applicationDate,
    Boolean resultPublished,
    Double totalScore,
    Set<Section> sections
) {

}
