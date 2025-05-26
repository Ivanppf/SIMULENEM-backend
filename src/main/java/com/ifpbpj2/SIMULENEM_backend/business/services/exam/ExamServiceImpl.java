package com.ifpbpj2.SIMULENEM_backend.business.services.exam;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.Exam;
import com.ifpbpj2.SIMULENEM_backend.model.repositories.exam.ExamRepository;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.ExamRequestDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.ExamResponseDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.QuestionExamResponseDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.SectionResponseDTO;

@Service
public class ExamServiceImpl implements ExamService {

    private ExamRepository examRepository;

    public ExamServiceImpl(ExamRepository examRepository) {
        this.examRepository = examRepository;

    }

    @Override
    public List<ExamResponseDTO> findAll() {
        return examRepository.findAll().stream()
            .map(ExamResponseDTO::new).toList();
    }

    @Override
    public Exam findById(UUID id) {
        return examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prova com id " + id + " não encontrada"));
    }

    @Override
    public ExamResponseDTO save(Exam exam) {
        return new ExamResponseDTO(examRepository.save(exam));
    }

    @Override
    public ExamResponseDTO update(UUID id, ExamRequestDTO examUpdate) {
        Exam exam = findById(id);
        exam.setApplicationDate(examUpdate.applicationDate());
        exam.setResultPublished(examUpdate.resultPublished());
        return new ExamResponseDTO(examRepository.save(exam));
    }

    @Override
    public void deleteById(UUID id) {
        findById(id);
        examRepository.deleteById(id);
    }

}
