package com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response;

import java.util.List;

public record UserPageableSuapDTO(int count, String next, String previous, List<UserSuapResponseDTO> results) {
}
