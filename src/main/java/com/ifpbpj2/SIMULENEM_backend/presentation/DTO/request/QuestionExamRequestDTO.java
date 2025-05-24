package com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request;

import java.util.UUID;

public record QuestionExamRequestDTO(
    int position,
    double scoreQuestion,
    UUID questionUuid
) {}
