package com.ifpbpj2.SIMULENEM_backend.business.services;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ifpbpj2.SIMULENEM_backend.model.entities.Category;
import com.ifpbpj2.SIMULENEM_backend.model.entities.Question;
import com.ifpbpj2.SIMULENEM_backend.model.repositories.QuestionRepository;

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
    public Question save(Question question, Set<String> categoryNames) {
        Set<Category> categories = categoryService.findByNameIn(categoryNames);
        question.setCategories(categories);
        return questionRepository.save(question);
    }

    @Override
    public List<Question> saveAll(List<Question> questionList) {
        return questionRepository.saveAll(questionList);
    }

    @Override
    public Question update(UUID uuid, Question question) {
        existsById(uuid);
        question.setId(uuid);
        return questionRepository.save(question);
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
