package com.ifpbpj2.SIMULENEM_backend.presentation.controllers.exam;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifpbpj2.SIMULENEM_backend.business.services.exam.ExamService;
import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.Exam;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.ExamRequestDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.ExamResponseDTO;

@RestController
@RequestMapping("/exams")
public class ExamControllerImpl {

    private final ExamService examService;

    public ExamControllerImpl(ExamService examService) {
        this.examService = examService;
    }

    @GetMapping
    public ResponseEntity<List<ExamResponseDTO>> findAll() {
        List<ExamResponseDTO> exams = examService.findAll();
        return ResponseEntity.ok().body(exams);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamResponseDTO> findById(@PathVariable("id") UUID id) {
        ExamResponseDTO exam = new ExamResponseDTO(examService.findById(id));
        return ResponseEntity.ok().body(exam);
    }

    @PostMapping
    public ResponseEntity<ExamResponseDTO> save(@RequestBody ExamRequestDTO obj) {
        Exam exam = new Exam(obj);
        ExamResponseDTO savedExam = examService.save(exam);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedExam);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamResponseDTO> update(@PathVariable("id") UUID id, @RequestBody ExamRequestDTO obj) {
        ExamResponseDTO updatedExam = examService.update(id, obj);
        return ResponseEntity.ok().body(updatedExam);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") UUID id) {
        examService.deleteById(id);
        return new ResponseEntity<>((HttpStatus.NO_CONTENT));
    }

}
