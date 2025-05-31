package com.ifpbpj2.SIMULENEM_backend.model.entities.exam;

import java.io.Serializable;
import java.util.UUID;

import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Question;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.QuestionExamRequestDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_QUESTION_EXAM")
public class QuestionExam implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Integer position;
 
    private Double scoreQuestion;

    @ManyToOne
    @JoinColumn(name = "QUESTION_ID")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "SECTION_ID")
    private Section section;
    
    public QuestionExam() {
    }
    
    public QuestionExam(int position, double scoreQuestion, Question question, Section section) {
        this.position = position;
        this.scoreQuestion = scoreQuestion;
        this.question = question;
        this.section = section;
    }
    
    public QuestionExam(QuestionExamRequestDTO obj, Question question) {
        this.position = obj.position();
        this.scoreQuestion = obj.scoreQuestion();
        this.question = question;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public Integer getPosition() {
        return position;
    }
    public void setPosition(Integer position) {
        this.position = position;
    }
    public Double getScoreQuestion() {
        return scoreQuestion;
    }
    public void setScoreQuestion(double scoreQuestion) {
        this.scoreQuestion = scoreQuestion;
    }
    public Question getQuestion() {
        return question;
    }
    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((question == null) ? 0 : question.hashCode());
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
        QuestionExam other = (QuestionExam) obj;
        if (question == null) {
            if (other.question != null)
                return false;
        } else if (!question.equals(other.question))
            return false;
        return true;
    }

    
}
