package com.ifpbpj2.SIMULENEM_backend.business.services.exam;

import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.Exam;
import com.ifpbpj2.SIMULENEM_backend.model.repositories.exam.projections.ExamProjection;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.ExamRequestDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.ExamResponseDTO;

public interface ExamService {

    Page<ExamProjection> findAll(Pageable pageable);

    Exam findById(UUID id);

    Set<Exam> findAllById(Set<UUID> ids);

    ExamResponseDTO save(Exam exam);

    ExamResponseDTO update(UUID id, ExamRequestDTO exam);

    void deleteById(UUID id);

}