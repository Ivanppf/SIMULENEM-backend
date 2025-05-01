package com.ifpbpj2.SIMULENEM_backend.business.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.ifpbpj2.SIMULENEM_backend.model.entities.Question;
import com.ifpbpj2.SIMULENEM_backend.model.repositories.QuestionRepository;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public List<Question> find(Question questionFilter) {

        Example example = Example.of(questionFilter,
                ExampleMatcher.matching()
                        .withIgnoreCase()
                        .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return questionRepository.findAll(example);

    }

    @Override
    public Question findById(UUID uuid) {

        return questionRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Question with id " + uuid + " not found"));
    }

    @Override
    public Question save(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public Question update(UUID uuid, Question question) {
        existsById(uuid);
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
