package com.ifpbpj2.SIMULENEM_backend.business.services.exam;

import java.util.List;
import java.util.UUID;

import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.Exam;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.ExamRequestDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.ExamResponseDTO;

public interface ExamService {

    List<ExamResponseDTO> findAll();

    Exam findById(UUID id);

    ExamResponseDTO save(Exam exam);

    ExamResponseDTO update(UUID id, ExamRequestDTO exam);

    void deleteById(UUID id);

}