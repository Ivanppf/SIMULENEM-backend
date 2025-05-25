package com.ifpbpj2.SIMULENEM_backend.model.repositories.exam;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.Exam;

public interface ExamRepository extends JpaRepository<Exam, UUID> {
    
}
