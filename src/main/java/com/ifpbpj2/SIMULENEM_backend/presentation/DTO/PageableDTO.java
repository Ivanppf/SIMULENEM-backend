package com.ifpbpj2.SIMULENEM_backend.presentation.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PageableDTO<T>(List<T> content, boolean first, boolean last, @JsonProperty("page") int number, int size,
                @JsonProperty("pageElements") int numberOfElements, int totalPages, long totalElements) {

}