package com.ifpbpj2.SIMULENEM_backend.business.services.exam;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.Exam;
import com.ifpbpj2.SIMULENEM_backend.model.repositories.exam.ExamRepository;

@Service
public class ExamServiceImpl implements ExamService {

    private ExamRepository examRepository;

    public ExamServiceImpl(ExamRepository examRepository) {
        this.examRepository = examRepository;

    }

    @Override
    public List<Exam> findAll() {
        return examRepository.findAll();
    }

    @Override
    public Exam findById(UUID id) {
        return examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prova com id " + id + " não encontrada"));
    }

    @Override
    public Exam save(Exam exam) {
        return examRepository.save(exam);
    }

    @Override
    public Exam update(UUID id, Exam exam) {
        findById(id);
        exam.setId(id);
        return examRepository.save(exam);
    }

    @Override
    public void deleteById(UUID id) {
        findById(id);
        examRepository.deleteById(id);
    }

}
