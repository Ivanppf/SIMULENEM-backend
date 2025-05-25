package com.ifpbpj2.SIMULENEM_backend.business.services.exam;

import java.util.List;
import java.util.UUID;

import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.Exam;

public interface ExamService {

    List<Exam> findAll();

    Exam findById(UUID id);

    Exam save(Exam exam);

    Exam update(UUID id, Exam exam);

    void deleteById(UUID id);

}