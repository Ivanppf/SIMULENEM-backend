package com.ifpbpj2.SIMULENEM_backend.presentation.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.ifpbpj2.SIMULENEM_backend.business.services.question.CategoryService;
import com.ifpbpj2.SIMULENEM_backend.business.services.question.QuestionService;
import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Category;
import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Question;
import com.ifpbpj2.SIMULENEM_backend.model.enums.Difficulty;
import com.ifpbpj2.SIMULENEM_backend.model.enums.QuestionType;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.QuestionRequestDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.CategoryResponseDTO;
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
    public ResponseEntity<Page<QuestionResponseDTO>> find(
            @ParameterObject Pageable pageable,
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
        Page<Question> questions = questionService.find(pageable, questionFilter);
        return ResponseEntity.ok().body(questions.map(QuestionResponseDTO::new));

    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponseDTO> findById(@PathVariable("id") UUID id) {
        Question question = questionService.findById(id);
        return ResponseEntity.ok().body(new QuestionResponseDTO(question));
    }

    @Override
    @PostMapping
    public ResponseEntity<QuestionResponseDTO> save(@RequestBody @Valid QuestionRequestDTO obj) {
        Set<CategoryResponseDTO> categories = obj.categories().stream().map(categoryName -> {
              return new CategoryResponseDTO(UUID.fromString(categoryName), null, null);
        }).collect(Collectors.toSet());
        Question question = new Question(obj);
        question = questionService.save(question, categories);
        return ResponseEntity.status(HttpStatus.CREATED).body(new QuestionResponseDTO(question));
    }

    @Override
    @PostMapping("/all")
    public ResponseEntity<List<QuestionResponseDTO>> saveAll(@RequestBody @Valid List<QuestionRequestDTO> objList) {
        List<Question> questionList = new ArrayList<>();
        objList.forEach(q -> {
            Question newQuestion = new Question(q);
            Set<Category> categories = q.categories().stream().map(c -> {
                Category newCategory = new Category();
                newCategory.setId(UUID.fromString(c));
                return newCategory;
            }).collect(Collectors.toSet());
            newQuestion.setCategories(categories);
            questionList.add(newQuestion);
        });
        List<Question> savedQuestions = questionService.saveAll(questionList);
        List<QuestionResponseDTO> savedQuestionsDTO = new ArrayList<>();
        savedQuestions.forEach(q -> {
            savedQuestionsDTO.add(new QuestionResponseDTO(q));
        });

        return ResponseEntity.status(HttpStatus.CREATED).body(savedQuestionsDTO);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponseDTO> update(@PathVariable("id") UUID id,
            @Valid @RequestBody QuestionRequestDTO obj) {
        Question question = questionService.update(id, obj);
        return ResponseEntity.ok().body(new QuestionResponseDTO(question));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<QuestionResponseDTO> deleteById(@PathVariable("id") UUID id) {
        questionService.deleteById(id);
        return new ResponseEntity<>((HttpStatus.NO_CONTENT));
    }

}
