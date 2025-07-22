package com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserSuapResponseDTO(

                @JsonProperty("uuid") String id,
                @JsonProperty("nome") String name,
                @JsonProperty("matricula") String registration

) {

}
