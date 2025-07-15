package com.ifpbpj2.SIMULENEM_backend.presentation.DTO.queryParameters;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import com.ifpbpj2.SIMULENEM_backend.model.enums.Difficulty;
import com.ifpbpj2.SIMULENEM_backend.model.enums.QuestionType;

public class QuestionQueryParametersDTO {

        private UUID id;
        private QuestionType questionType;
        private String title;
        private Set<String> categoryNames;
        private Difficulty difficulty;
        private LocalDateTime lastUsedDate;

        public UUID getId() {
                return id;
        }

        public void setId(UUID id) {
                this.id = id;
        }

        public QuestionType getQuestionType() {
                return questionType;
        }

        public void setQuestionType(QuestionType questionType) {
                this.questionType = questionType;
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public Set<String> getCategoryNames() {
                return categoryNames;
        }

        public void setCategoryNames(Set<String> categoryNames) {
                this.categoryNames = categoryNames;
        }

        public Difficulty getDifficulty() {
                return difficulty;
        }

        public void setDifficulty(Difficulty difficulty) {
                this.difficulty = difficulty;
        }

        public LocalDateTime getLastUsedDate() {
                return lastUsedDate;
        }

        public void setLastUsedDate(LocalDateTime lastUsedDate) {
                this.lastUsedDate = lastUsedDate;
        }

}
