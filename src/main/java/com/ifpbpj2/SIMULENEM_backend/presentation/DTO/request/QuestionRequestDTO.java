package com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request;

import java.time.LocalDateTime;
import java.util.Set;

import com.ifpbpj2.SIMULENEM_backend.model.entities.Alternative;
import com.ifpbpj2.SIMULENEM_backend.model.entities.Illustration;
import com.ifpbpj2.SIMULENEM_backend.model.enums.Difficulty;
import com.ifpbpj2.SIMULENEM_backend.model.enums.QuestionType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record QuestionRequestDTO(
        @NotNull(message = "Por favor informe o tipo da questão") QuestionType questionType,
        @NotBlank(message = "Por favor informe o título da questão") String title,
        Illustration illustration,
        @NotNull(message = "Por favor informe as alternativas") Set<Alternative> alternatives,
        @NotNull(message = "Por favor informe a dificuldade da questão") Difficulty difficulty,
        @NotBlank(message = "Por favor informe a resposta esperada") String expectedAnswer) {

}
