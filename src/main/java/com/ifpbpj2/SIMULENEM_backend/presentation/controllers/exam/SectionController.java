package com.ifpbpj2.SIMULENEM_backend.presentation.controllers.exam;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifpbpj2.SIMULENEM_backend.business.services.exam.SectionService;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.AddQuestionsDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.SectionRequestDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.QuestionExamResponseDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.SectionResponseDTO;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "exams/{examUuid}/sections")
public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @PostMapping
    public ResponseEntity<SectionResponseDTO> save(
            @PathVariable(required = true) UUID examUuid,
            @RequestBody SectionRequestDTO requestDTO) {
        SectionResponseDTO responseDTO = sectionService.save(examUuid, requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping(value = "/{sectionUuid}")
    public ResponseEntity<SectionResponseDTO> update(
            @PathVariable UUID examUuid,
            @PathVariable UUID sectionUuid,
            @RequestBody SectionRequestDTO requestDTO) {
        SectionResponseDTO responseDTO = sectionService.update(examUuid, sectionUuid, requestDTO);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<SectionResponseDTO>> findAll(@PathVariable UUID examUuid) {
        List<SectionResponseDTO> responseDTOs = sectionService.findSectionsByExamId(examUuid);
        return ResponseEntity.ok().body(responseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SectionResponseDTO> findById(@PathVariable UUID id) {
        var section = sectionService.findById(id);
        var response = new SectionResponseDTO(section.getId(), section.getPosition(), section.getTitle(),
                section.getQuestionExams().stream().map(questionExam -> new QuestionExamResponseDTO(questionExam))
                        .collect(Collectors.toList()));
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping(value = "{sectionUuid}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID sectionUuid) {
        sectionService.deleteById(sectionUuid);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> addQuestions(@PathVariable UUID id, @RequestBody @Valid AddQuestionsDTO request) {
        sectionService.addQuestions(id, request);
        return ResponseEntity.noContent().build();
    }
}
