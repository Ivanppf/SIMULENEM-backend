package com.ifpbpj2.SIMULENEM_backend.business.services.exam;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.Exam;
import com.ifpbpj2.SIMULENEM_backend.model.repositories.exam.ExamRepository;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.ExamRequestDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.ExamResponseDTO;

@Service
public class ExamServiceImpl implements ExamService {

    private ExamRepository examRepository;

    public ExamServiceImpl(ExamRepository examRepository) {
        this.examRepository = examRepository;

    }

    @Override
    public Page<ExamResponseDTO> findAll(Pageable pageable, Exam examFilter) {
        Example example = Example.of(examFilter,
                ExampleMatcher.matching()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));

        Page<Exam> exams = examRepository.findAll(example, pageable);
        return exams.map(exam -> new ExamResponseDTO(exam, null));
    }


    @Override
    public Set<Exam> findAllById(Set<UUID> ids) {
        return examRepository.findAllById(ids).stream().collect(Collectors.toSet());
    }

    @Override
    public Exam findById(UUID id) {
        return examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prova com id " + id + " não encontrada"));
    }

    @Override
    public ExamResponseDTO save(Exam exam) {
        return new ExamResponseDTO(examRepository.save(exam), null);
    }

    @Override
    public ExamResponseDTO update(UUID id, ExamRequestDTO examUpdate) {
        Exam exam = findById(id);
        exam.setTitle(examUpdate.title());
        exam.setApplicationDate(examUpdate.applicationDate());
        exam.setResultPublished(examUpdate.resultPublished());
        return new ExamResponseDTO(examRepository.save(exam), null);
    }

    @Override
    public void deleteById(UUID id) {
        findById(id);
        examRepository.deleteById(id);
    }

}
