package com.ifpbpj2.SIMULENEM_backend.business.services;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ifpbpj2.SIMULENEM_backend.model.entities.Question;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.CategoryResponseDTO;

public interface QuestionService {

    Page<Question> find(Pageable pageable, Question question);

    Question findById(UUID uuid);

    Question save(Question question, Set<CategoryResponseDTO> categoryNames);

    List<Question> saveAll(List<Question> questionList);

    Question update(UUID uuid, Question question);

    void deleteById(UUID uuid);

    void existsById(UUID uuid);

}
