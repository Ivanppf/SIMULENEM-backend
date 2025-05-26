package com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request;

import java.time.LocalDate;

public record ExamRequestDTO(
    LocalDate applicationDate,
    Boolean resultPublished
) {

}
