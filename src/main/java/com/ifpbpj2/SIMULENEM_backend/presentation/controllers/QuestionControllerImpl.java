package com.ifpbpj2.SIMULENEM_backend.presentation.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.SessionScope;

import com.ifpbpj2.SIMULENEM_backend.business.services.CategoryService;
import com.ifpbpj2.SIMULENEM_backend.business.services.QuestionService;
import com.ifpbpj2.SIMULENEM_backend.model.entities.Category;
import com.ifpbpj2.SIMULENEM_backend.model.entities.Question;
import com.ifpbpj2.SIMULENEM_backend.model.enums.Difficulty;
import com.ifpbpj2.SIMULENEM_backend.model.enums.QuestionType;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.QuestionRequestDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.QuestionResponseDTO;

import jakarta.validation.Valid;

@RestController
@SessionScope
@RequestMapping("/questions")
public class QuestionControllerImpl implements QuestionController {

    private final QuestionService questionService;
    private final CategoryService categoryService;

    public QuestionControllerImpl(QuestionService questionService, CategoryService categoryService) {
        this.questionService = questionService;
        this.categoryService = categoryService;
    }

    @Override
    @GetMapping
    public ResponseEntity<List<QuestionResponseDTO>> find(
            @RequestParam(value = "id", required = false) UUID id,
            @RequestParam(value = "questionType", required = false) QuestionType questionType,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "categories", required = false) Set<String> categoryNames,
            @RequestParam(value = "difficulty", required = false) Difficulty difficulty,
            @RequestParam(value = "lastUsedDate", required = false) LocalDateTime lastUsedDate) {

        Set<Category> categories = null;
        if (categoryNames != null) {
            categories = categoryService.findByNameIn(categoryNames);
        }
        Question questionFilter = new Question(id, questionType, title, categories, difficulty, lastUsedDate);
        List<Question> questions = questionService.find(questionFilter);
        return ResponseEntity.ok().body(questions.stream().map(QuestionResponseDTO::new).toList());

    }

    @Override
    @PostMapping
    public ResponseEntity<QuestionResponseDTO> save(@RequestBody @Valid QuestionRequestDTO obj) {
        Question question = new Question(obj);
        question.setCategories(obj.categories().stream().map(Category::new).collect(Collectors.toSet()));
        question = questionService.save(question);
        return ResponseEntity.status(HttpStatus.CREATED).body(new QuestionResponseDTO(question));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponseDTO> update(@PathVariable("id") UUID id,
            @Valid @RequestBody QuestionRequestDTO obj) {
        Question question = new Question(obj);
        question = questionService.update(id, question);
        return ResponseEntity.ok().body(new QuestionResponseDTO(question));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<QuestionResponseDTO> deleteById(@PathVariable("id") UUID id) {
        questionService.deleteById(id);
        return new ResponseEntity<>((HttpStatus.NO_CONTENT));
    }

}
