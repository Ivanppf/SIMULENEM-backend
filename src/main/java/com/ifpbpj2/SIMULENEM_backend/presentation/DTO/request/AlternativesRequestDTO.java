package com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request;

import com.ifpbpj2.SIMULENEM_backend.model.entities.Illustration;

import jakarta.validation.constraints.NotNull;

public record AlternativesRequestDTO(
                @NotNull(message = "Por favor informe uma letra") char options,
                @NotNull(message = "Por favor informe um texto para a alternativa") String text,
                IllustrationRequestDTO illustration) {

}
