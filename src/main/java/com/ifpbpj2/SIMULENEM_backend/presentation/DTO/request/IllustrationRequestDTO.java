package com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request;

import jakarta.validation.constraints.NotNull;

public record IllustrationRequestDTO(
        @NotNull(message = "Por favor informe uma descrição para a ilustração") String description,
        @NotNull(message = "Por favor informe a url da ilustração") String url) {

}
