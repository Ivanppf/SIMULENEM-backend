package com.ifpbpj2.SIMULENEM_backend.presentation.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.SessionScope;

import com.ifpbpj2.SIMULENEM_backend.business.services.QuestionService;
import com.ifpbpj2.SIMULENEM_backend.business.services.QuestionServiceImpl;
import com.ifpbpj2.SIMULENEM_backend.model.entities.Question;
import com.ifpbpj2.SIMULENEM_backend.model.enums.Difficulty;
import com.ifpbpj2.SIMULENEM_backend.model.enums.QuestionType;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.QuestionRequestDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.QuestionResponseDTO;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@SessionScope
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public ResponseEntity find(
            @RequestParam(value = "id", required = false) UUID id,
            @RequestParam(value = "questionType", required = false) QuestionType questionType,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "difficulty", required = false) Difficulty difficulty,
            @RequestParam(value = "estimatedTimeInMin", required = false) Integer estimatedTimeInMin,
            @RequestParam(value = "lastUsedDate", required = false) LocalDateTime lastUsedDate) {

        Question questionFilter = new Question(id, questionType, title, difficulty, estimatedTimeInMin, lastUsedDate);
        List<Question> question = questionService.find(questionFilter);
        return ResponseEntity.ok().body(question.stream().map(QuestionResponseDTO::new).toList());

    }

    @PostMapping
    public ResponseEntity save(@Valid @RequestBody QuestionRequestDTO obj) {
        Question question = new Question();
    }

    // @PutMapping

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") UUID id) {
        questionService.deleteById(id);
        return new ResponseEntity<>((HttpStatus.NO_CONTENT));
    }

}
