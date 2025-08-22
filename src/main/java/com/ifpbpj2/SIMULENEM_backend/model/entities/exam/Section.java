package com.ifpbpj2.SIMULENEM_backend.model.entities.exam;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "TB_SECTION")
public class Section implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Integer position;


    private String title;

    @Column(length = 4)
    private String titleAbbreviation;


    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL)
    private Set<QuestionExam> questionExams = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "EXAM_ID")
    private Exam exam;

    public Section() {
    }

    public Section(Integer position, String title) {
        this.position = position;
        this.title = title;
    }

    public void addQuestionExams(QuestionExam questionExam){
        questionExam.setSection(this);
        this.questionExams.add(questionExam);
    }

    public double getScoreSection(){
        return questionExams.stream().mapToDouble((QuestionExam::getScoreQuestion)).sum();
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
    public Set<QuestionExam> getQuestionExams() {
        return questionExams;
    }
    public void setQuestionExams(Set<QuestionExam> questions) {
        this.questionExams = questions;
    }
    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }


    public String getTitleAbbreviation() {
        return titleAbbreviation;
    }

    public void setTitleAbbreviation(String titleAbbreviation) {
        this.titleAbbreviation = titleAbbreviation;
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
