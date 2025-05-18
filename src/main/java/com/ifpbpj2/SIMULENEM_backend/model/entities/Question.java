package com.ifpbpj2.SIMULENEM_backend.model.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.ifpbpj2.SIMULENEM_backend.model.enums.Difficulty;
import com.ifpbpj2.SIMULENEM_backend.model.enums.QuestionType;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.QuestionRequestDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_QUESTION")
public class Question implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionType questionType;

    @Column(nullable = false)
    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "illustration_id", referencedColumnName = "id")
    private Illustration illustration;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Alternative> alternatives;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;

    @Column(nullable = false)
    private String expectedAnswer;

    private LocalDateTime lastUsedDate;

    @ManyToMany
    @Column(nullable = false)
    @JoinTable(
        name = "TB_CATEGORY_QUESTION",
        joinColumns = @JoinColumn(name = "question_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    public Question() {
    }

    public Question(QuestionType questionType, String title, Illustration illustration,
            Set<Alternative> alternatives, Set<Category> categories, Difficulty difficulty,
            String expectedAnswer) {
        this.questionType = questionType;
        this.title = title;
        this.illustration = illustration;
        this.alternatives = alternatives;
        this.categories = categories;
        this.difficulty = difficulty;
        this.expectedAnswer = expectedAnswer;
    }

    public Question(UUID id, QuestionType questionType, String title, Set<Category> categories, Difficulty difficulty,
            LocalDateTime lastUsedDate) {
        this.id = id;
        this.questionType = questionType;
        this.title = title;
        this.categories = categories;
        this.difficulty = difficulty;
        this.lastUsedDate = lastUsedDate;
    }

    public Question(QuestionRequestDTO obj) {
        this.questionType = obj.questionType();
        this.title = obj.title();
        if (obj.illustration() != null) {
            this.illustration = new Illustration(obj.illustration());
        }
        this.alternatives = obj.alternatives().stream().map(Alternative::new).collect(Collectors.toSet());
        this.difficulty = obj.difficulty();
        this.expectedAnswer = obj.expectedAnswer();
    }

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

    public Set<Alternative> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(Set<Alternative> alternatives) {
        this.alternatives = alternatives;
    }

    public Illustration getIllustration() {
        return illustration;
    }

    public void setIllustration(Illustration illustration) {
        this.illustration = illustration;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public String getExpectedAnswer() {
        return expectedAnswer;
    }

    public void setExpectedAnswer(String expectedAnswer) {
        this.expectedAnswer = expectedAnswer;
    }

    public LocalDateTime getLastUsedDate() {
        return lastUsedDate;
    }

    public void setLastUsedDate(LocalDateTime lastUsedDate) {
        this.lastUsedDate = lastUsedDate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Question other = (Question) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Question [id=" + id + ", questionType=" + questionType + ", title=" + title + ", illustration="
                + illustration + ", alternatives=" + alternatives + ", difficulty=" + difficulty + ", expectedAnswer="
                + expectedAnswer + ", lastUsedDate=" + lastUsedDate + ", categories=" + categories + "]";
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

}
