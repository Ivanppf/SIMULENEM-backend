package com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request;

import jakarta.validation.constraints.NotNull;

public record SectionRequestDTO(
                @NotNull Integer position,
                String title) {

}
