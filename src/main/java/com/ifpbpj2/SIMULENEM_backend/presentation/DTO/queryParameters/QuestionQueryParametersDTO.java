package com.ifpbpj2.SIMULENEM_backend.presentation.DTO.queryParameters;

import java.time.LocalDateTime;
import java.util.Set;

import com.ifpbpj2.SIMULENEM_backend.model.enums.Difficulty;
import com.ifpbpj2.SIMULENEM_backend.model.enums.QuestionType;

public class QuestionQueryParametersDTO {

        private QuestionType questionType;
        private String title;
        private Set<String> categoryNames;
        private Difficulty difficulty;
        private LocalDateTime lastUsedDate;

        public QuestionQueryParametersDTO(QuestionType questionType, String title, Set<String> categoryNames,
                        Difficulty difficulty, LocalDateTime lastUsedDate) {
                this.questionType = questionType;
                this.title = title;
                this.categoryNames = categoryNames;
                this.difficulty = difficulty;
                this.lastUsedDate = lastUsedDate;
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