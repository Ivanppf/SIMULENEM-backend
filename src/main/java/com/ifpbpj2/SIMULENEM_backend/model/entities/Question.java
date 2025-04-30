package com.ifpbpj2.SIMULENEM_backend.model.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.ifpbpj2.SIMULENEM_backend.model.enums.Difficulty;
import com.ifpbpj2.SIMULENEM_backend.model.enums.QuestionType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_QUESTION")
public class Question implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionType questionType;
    
    @Column(nullable = false)
    private String statement;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "illustration_id", referencedColumnName = "id")
    private Illustration illustration;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Alternative> alternatives;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;
    
    @Column(nullable = false)
    private int estimatedTimeInMin;
    
    @Column(nullable = false)
    private String expectedAnswer;
    
    @Column(nullable = true)
    private LocalDateTime lastUsedDate;

    public Question() {
        this.alternatives = new HashSet<>();
    }

    public Question(QuestionType questionType, String statement, Illustration illustration, 
            Set<Alternative> alternatives, Difficulty difficulty, int estimatedTimeInMin,
            String expectedAnswer) {
        this.questionType = questionType;
        this.statement = statement;
        this.illustration = illustration;
        this.alternatives = alternatives;
        this.difficulty = difficulty;
        this.estimatedTimeInMin = estimatedTimeInMin;
        this.expectedAnswer = expectedAnswer;
    }

    public UUID getId() {
        return id;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
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

    public int getEstimatedTimeInMin() {
        return estimatedTimeInMin;
    }

    public void setEstimatedTimeInMin(int estimatedTimeInMin) {
        this.estimatedTimeInMin = estimatedTimeInMin;
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
        return "Question [id=" + id + ", questionType=" + questionType + ", statement=" + statement + ", difficulty="
                + difficulty + ", estimatedTimeInMin=" + estimatedTimeInMin + ", expectedAnswer=" + expectedAnswer
                + ", lastUsedDate=" + lastUsedDate + "]";
    }

    

}
