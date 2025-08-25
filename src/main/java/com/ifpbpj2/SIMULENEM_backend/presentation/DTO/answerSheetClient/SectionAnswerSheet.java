package com.ifpbpj2.SIMULENEM_backend.presentation.DTO.answerSheetClient;

import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.Section;

public record SectionAnswerSheet(
        String nome,
        String abreviacao,
        int totalQuestoes) {
    public SectionAnswerSheet(Section section) {
        this(section.getTitle(), section.getTitleAbbreviation(), section.getQuestionExams().size());
    }
}
