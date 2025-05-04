package com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response;

import java.util.UUID;

import com.ifpbpj2.SIMULENEM_backend.model.entities.Category;
import com.ifpbpj2.SIMULENEM_backend.model.enums.Origin;

public record CategoryResponseDTO(
        UUID id,
        String name,
        Origin origin) {
    public CategoryResponseDTO(Category category) {
        this(
                category.getId(),
                category.getName(),
                category.getOrigin());
    }
}
