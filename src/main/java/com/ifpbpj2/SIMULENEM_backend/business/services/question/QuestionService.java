package com.ifpbpj2.SIMULENEM_backend.business.services.question;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Question;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.queryParameters.QuestionQueryParametersDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.QuestionRequestDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.CategoryResponseDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.QuestionResponseDTO;

public interface QuestionService {

    Page<QuestionResponseDTO> findAll(Pageable pageable, QuestionQueryParametersDTO queryParametersDTO);

    Question findById(UUID uuid);

    Question save(Question question, Set<CategoryResponseDTO> categoryNames);

    List<Question> saveAll(List<Question> questionList);

    Question update(UUID uuid, QuestionRequestDTO question);

    void deleteById(UUID uuid);

    void existsById(UUID uuid);

}
