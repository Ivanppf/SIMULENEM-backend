package com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(

                @NotBlank String username,
                @NotBlank String password

) {

}
