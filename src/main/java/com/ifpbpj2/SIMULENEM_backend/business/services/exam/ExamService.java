package com.ifpbpj2.SIMULENEM_backend.business.services.exam;

import java.util.Set;
import java.util.UUID;

import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.answerSheetClient.GeracaoCartaoRespostaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.Exam;
import com.ifpbpj2.SIMULENEM_backend.model.repositories.exam.projections.ExamProjection;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.ExamRequestDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.ExamResponseDTO;
import org.springframework.http.ResponseEntity;

public interface ExamService {

    Page<ExamProjection> findAll(Pageable pageable);

    Byte[] gerarCartaoResposta(UUID idExam);

    Exam findById(UUID id);

    Set<Exam> findAllById(Set<UUID> ids);

    ExamResponseDTO save(Exam exam);

    ExamResponseDTO update(UUID id, ExamRequestDTO exam);

    void deleteById(UUID id);

    public byte[] generatePdf(UUID id);

}