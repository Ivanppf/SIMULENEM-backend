package com.ifpbpj2.SIMULENEM_backend.presentation.controllers.exam;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ifpbpj2.SIMULENEM_backend.business.services.exam.ExamService;
import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.Exam;
import com.ifpbpj2.SIMULENEM_backend.model.repositories.exam.projections.ExamProjection;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.PageableDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.PageableMapper;
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
    public ResponseEntity<PageableDTO<ExamProjection>> findAll(Pageable pageable) {
        var exams = examService.findAll(pageable);
        return ResponseEntity.ok().body(PageableMapper.toDTO(exams));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamResponseDTO> findById(@PathVariable("id") UUID id) {
        Exam exam = examService.findById(id);
        return ResponseEntity.ok().body(new ExamResponseDTO(exam));
    }

    @GetMapping("/{id}/answer-sheet")
    public ResponseEntity<Byte[]> downloadArquivo(@PathVariable("id") UUID id) {
        Byte[] arquivo = examService.gerarCartaoResposta(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "arquivo.pdf");

        return new ResponseEntity<>(arquivo, headers, HttpStatus.OK);
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
