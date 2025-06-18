package com.ifpbpj2.SIMULENEM_backend.model.entities.classgroup;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.Exam;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.ClassGroupRequestDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class ClassGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String gradeLevel;
    private char identifier;
    private int room;
    private int block;

    @OneToMany
    @JoinColumn(name = "exam_id")
    private Set<Exam> exams;

    public ClassGroup(ClassGroupRequestDTO dto) {
        this.gradeLevel = dto.gradeLevel();
        this.identifier = dto.identifier();
        this.room = dto.room();
        this.block = dto.block();
    }

    public ClassGroup(String gradeLevel, char identifier, int room, int block) {
        this.gradeLevel = gradeLevel;
        this.identifier = identifier;
        this.room = room;
        this.block = block;
        this.exams = new HashSet<>();
    }
    public String getGradeLevel() {
        return gradeLevel;
    }
    public void setGradeLevel(String gradeLevel) {
        this.gradeLevel = gradeLevel;
    }
    public char getIdentifier() {
        return identifier;
    }
    public void setIdentifier(char identifier) {
        this.identifier = identifier;
    }
    public int getRoom() {
        return room;
    }
    public void setRoom(int room) {
        this.room = room;
    }
    public int getBlock() {
        return block;
    }
    public void setBlock(int building) {
        this.block = building;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public void setExams(Set<Exam> exams) {
        this.exams = exams;
    }

    
}
