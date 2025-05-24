package com.ifpbpj2.SIMULENEM_backend.model.entities.exam;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.SectionRequestDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_SECTION")
public class Section implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Integer position;


    private String title;

    @OneToMany(mappedBy = "section")
    private Set<QuestionExam> questions;

    public Section() {
    }

    public Section(Integer position, String title, Set<QuestionExam> questions) {
        this.position = position;
        this.title = title;
        this.questions = questions;
    }

    public double getScoreSection(){
        return questions.stream().mapToDouble((QuestionExam::getScoreQuestion)).sum();
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
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Set<QuestionExam> getQuestions() {
        return questions;
    }
    public void setQuestions(Set<QuestionExam> questions) {
        this.questions = questions;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((position == null) ? 0 : position.hashCode());
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
        Section other = (Section) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (position == null) {
            if (other.position != null)
                return false;
        } else if (!position.equals(other.position))
            return false;
        return true;
    }



    
}
