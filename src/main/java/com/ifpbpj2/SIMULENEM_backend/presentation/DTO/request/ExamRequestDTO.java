package com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request;

import java.time.LocalDate;

public record ExamRequestDTO(
    String title,
    LocalDate applicationDate,
    Boolean resultPublished
) {

}
