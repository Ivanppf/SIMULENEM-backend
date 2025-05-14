package com.ifpbpj2.SIMULENEM_backend.business.services;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.ifpbpj2.SIMULENEM_backend.model.entities.Question;

public interface QuestionService {

    List<Question> find(Question question);

    Question findById(UUID uuid);

    Question save(Question question, Set<String> categoryNames);

    List<Question> saveAll(List<Question> questionList);

    Question update(UUID uuid, Question question);

    void deleteById(UUID uuid);

    void existsById(UUID uuid);

}
