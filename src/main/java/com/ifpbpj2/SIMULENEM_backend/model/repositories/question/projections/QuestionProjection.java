package com.ifpbpj2.SIMULENEM_backend.model.repositories.question.projections;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Alternative;
import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Illustration;
import com.ifpbpj2.SIMULENEM_backend.model.enums.Difficulty;
import com.ifpbpj2.SIMULENEM_backend.model.enums.QuestionType;

public interface QuestionProjection {

        UUID getId();

        QuestionType getQuestionType();

        String getTitle();

        Illustration getIllustration();

        Set<Alternative> getAlternatives();

        Set<CategoryProjection> getCategories();

        Difficulty getDifficulty();

        String getExpectedAnswer();

        LocalDateTime getLastUsedDate();

}
