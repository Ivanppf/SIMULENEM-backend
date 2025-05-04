package com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request;

import com.ifpbpj2.SIMULENEM_backend.model.enums.Origin;

public record CategoryRequestDTO(
        String name,
        Origin origin
) {
    
}
