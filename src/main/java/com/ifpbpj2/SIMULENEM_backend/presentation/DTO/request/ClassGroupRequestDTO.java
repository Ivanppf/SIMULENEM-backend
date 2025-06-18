package com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request;

import java.util.Set;
import java.util.UUID;

public record ClassGroupRequestDTO(

        String gradeLevel,
        char identifier,
        int room,
        int block,
        Set<UUID> examIds

) {

}
