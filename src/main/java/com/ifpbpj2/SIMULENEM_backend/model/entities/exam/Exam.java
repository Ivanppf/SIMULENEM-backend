package com.ifpbpj2.SIMULENEM_backend.model.entities.exam;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.ExamRequestDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "TB_EXAM")
public class Exam implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Temporal(TemporalType.DATE)
    private LocalDate applicationDate;
    private boolean resultPublished;

    @OneToMany
    @JoinColumn(name = "SECTION_ID")
    private Set<Section> sections;
    
    public Exam() {
    }
    
    public Exam(ExamRequestDTO obj) {
        this.applicationDate = obj.applicationDate();
        this.resultPublished = obj.resultPublished();
        this.sections = obj.sections();
    }

    public Exam(LocalDate applicationDate, boolean resultPublished, Set<Section> sections) {
        this.applicationDate = applicationDate;
        this.resultPublished = resultPublished;
        this.sections = sections;
    }

    public double getTotalScore() {
        return sections.stream().mapToDouble(Section::getScoreSection).sum();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public boolean isResultPublished() {
        return resultPublished;
    }

    public void setResultPublished(boolean resultPublished) {
        this.resultPublished = resultPublished;
    }

    public Set<Section> getSections() {
        return sections;
    }

    public void setSections(Set<Section> sections) {
        this.sections = sections;
    }

    
}
