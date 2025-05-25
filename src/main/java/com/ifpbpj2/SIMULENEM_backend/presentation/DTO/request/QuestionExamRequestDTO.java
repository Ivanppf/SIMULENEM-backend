package com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request;

import java.util.UUID;

public record QuestionExamRequestDTO(
    Integer position,
    Double scoreQuestion,
    UUID questionUuid
) {}
