package com.ifpbpj2.SIMULENEM_backend.model.repositories.exam;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.Exam;
import com.ifpbpj2.SIMULENEM_backend.model.repositories.exam.projections.ExamProjection;

public interface ExamRepository extends JpaRepository<Exam, UUID> {
        @Query("SELECT e FROM Exam e")
        Page<ExamProjection> findAllPageable(Pageable pageable);
}
