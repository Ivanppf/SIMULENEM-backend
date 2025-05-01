package com.ifpbpj2.SIMULENEM_backend.business.services;

import java.util.Locale.Category;

import org.springframework.stereotype.Service;

import com.ifpbpj2.SIMULENEM_backend.model.entities.Alternative;
import com.ifpbpj2.SIMULENEM_backend.model.entities.Question;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.QuestionRequestDTO;

@Service
public class ConverterService {
    
    public Question dtoToQuestion(QuestionRequestDTO questionRequestDTO) {
        Alternative alternative = alternativeService;
    }

}
