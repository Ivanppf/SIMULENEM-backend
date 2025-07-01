package com.ifpbpj2.SIMULENEM_backend.model.entities.exam;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.ExamRequestDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    private String title;

    @Temporal(TemporalType.DATE)
    private LocalDate applicationDate;
    private boolean resultPublished;

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL)
    private Set<Section> sections;

    public Exam() {
    }

    public Exam(ExamRequestDTO obj) {
        this.title = obj.title();
        this.applicationDate = obj.applicationDate();
        this.resultPublished = obj.resultPublished();
    }

    public Exam(String title, LocalDate applicationDate, boolean resultPublished) {
        this.title = title;
        this.applicationDate = applicationDate;
        this.resultPublished = resultPublished;
        this.sections = new HashSet<>();
    }

    public void addSection(Section section) {
        section.setExam(this);
        sections.add(section);
    }

    public Double getTotalScore() {
        if (sections == null) {
            return Double.valueOf(0);
        }
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        Exam other = (Exam) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
