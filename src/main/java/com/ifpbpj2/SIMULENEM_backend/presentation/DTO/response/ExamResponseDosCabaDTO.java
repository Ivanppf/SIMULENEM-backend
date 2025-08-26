package com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response;

import java.util.List;
import java.util.UUID;

public record ExamResponseDosCabaDTO(

                UUID idProva,
                String tituloSimulado,
                int numeroTotalQuestoes,
                int numeroAlternativas,
                List<SectionResponseDosCabaDTO> secoes

) {

}
