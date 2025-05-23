package com.ifpbpj2.SIMULENEM_backend.business.services;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Category;
import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Question;
import com.ifpbpj2.SIMULENEM_backend.model.repositories.QuestionRepository;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.QuestionRequestDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.CategoryResponseDTO;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final CategoryService categoryService;

    public QuestionServiceImpl(QuestionRepository questionRepository, CategoryService categoryService) {
        this.questionRepository = questionRepository;
        this.categoryService = categoryService;
    }

    @Override
    public Page<Question> find(Pageable pageable, Question questionFilter) {

        Example example = Example.of(questionFilter,
                ExampleMatcher.matching()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return questionRepository.findAll(example, pageable);

    }

    @Override
    public Question findById(UUID uuid) {

        return questionRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Question with id " + uuid + " not found"));
    }

    @Override
    public Question save(Question question, Set<CategoryResponseDTO> categoryDTOList) {
        Set<Category> categories = categoryDTOList.stream().map(c -> {
            Category newCategory = new Category(c.name(), c.origin());
            newCategory.setId(c.id());
            return newCategory;
        }).collect(Collectors.toSet());
        question.setCategories(categories);
        return questionRepository.save(question);
    }

    @Override
    public List<Question> saveAll(List<Question> questionList) {
        return questionRepository.saveAll(questionList);
    }

    @Override
    public Question update(UUID uuid, QuestionRequestDTO question) {
        existsById(uuid);
        Set<Category> categories = question.categories().stream().map(
                category -> categoryService.findById(UUID.fromString(category)))
                .collect(Collectors.toSet());
        Question questionUpdate = new Question(question);
        questionUpdate.setCategories(categories);
        questionUpdate.setId(uuid);
        return questionRepository.save(questionUpdate);
    }

    @Override
    public void deleteById(UUID uuid) {
        existsById(uuid);
        questionRepository.deleteById(uuid);
    }

    @Override
    public void existsById(UUID uuid) {
        if (!questionRepository.existsById(uuid)) {
            throw new RuntimeException("Question with id " + uuid + " not found");
        }
    }

}
