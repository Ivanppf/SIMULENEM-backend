package com.ifpbpj2.SIMULENEM_backend.presentation.DTO.answerSheetClient;

import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.QuestionExam;

public record QuestionExamDosCabaDTO(

                int posicao,
                String alternativaCorreta

) {
        public QuestionExamDosCabaDTO(QuestionExam questionExam) {
                this(questionExam.getPosition(), questionExam.getQuestion().getExpectedAnswer());
        }
}
