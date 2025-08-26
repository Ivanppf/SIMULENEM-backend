package com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request;

import java.util.Set;

import jakarta.validation.constraints.NotNull;

public record SectionRequestDTO(

                @NotNull Integer position,
                String title,
                String titleAbbreviation,
                Set<QuestionExamRequestDTO> questionExams

) {

}
