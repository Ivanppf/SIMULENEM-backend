package com.ifpbpj2.SIMULENEM_backend.tests.business.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.Set;

import com.ifpbpj2.SIMULENEM_backend.model.repositories.exam.SectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.ifpbpj2.SIMULENEM_backend.business.services.exam.ExamService;
import com.ifpbpj2.SIMULENEM_backend.business.services.exam.ExamServiceImpl;
import com.ifpbpj2.SIMULENEM_backend.business.services.exam.SectionService;
import com.ifpbpj2.SIMULENEM_backend.business.services.question.CategoryService;
import com.ifpbpj2.SIMULENEM_backend.business.services.question.CategoryServiceImpl;
import com.ifpbpj2.SIMULENEM_backend.business.services.question.QuestionService;
import com.ifpbpj2.SIMULENEM_backend.business.services.question.QuestionServiceImpl;
import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.Exam;
import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.QuestionExam;
import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.Section;
import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Question;
import com.ifpbpj2.SIMULENEM_backend.model.enums.Difficulty;
import com.ifpbpj2.SIMULENEM_backend.model.enums.QuestionType;
import com.ifpbpj2.SIMULENEM_backend.model.repositories.exam.ExamRepository;
import com.ifpbpj2.SIMULENEM_backend.model.repositories.question.CategoryRepository;
import com.ifpbpj2.SIMULENEM_backend.model.repositories.question.QuestionRepository;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.AlternativesRequestDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.IllustrationRequestDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.QuestionExamRequestDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.QuestionRequestDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.SectionRequestDTO;

import jakarta.transaction.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
class SectionServiceTest {
    
    @Autowired
    SectionRepository sectionRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ExamRepository examRepository;

    
    CategoryService categoryService;
    QuestionService questionService;
    SectionService sectionService;
    ExamService examService;

    @Autowired
    TestEntityManager entityManager;

    @BeforeEach
    void setUp(){
        categoryService = new CategoryServiceImpl(categoryRepository);
        questionService = new QuestionServiceImpl(questionRepository, categoryService);
        examService = new ExamServiceImpl(examRepository);
        sectionService = new SectionService(sectionRepository, examService, questionService );
    }

    // @Test
    // void testSave() {
    
    //     var question = new Question(questionRequestDTO);
    //     var questSave = entityManager.persistAndFlush(question);
    //     var exam = new Exam("titulo",LocalDate.now(), false);
    //     var examSave = entityManager.persistAndFlush(exam);

    //     QuestionExamRequestDTO questionExamRequestDTO = new QuestionExamRequestDTO(Integer.valueOf(1), Double.valueOf(5), questSave.getId());
    //     SectionRequestDTO sRequestDTO = new SectionRequestDTO(1, "AASD", Set.of(questionExamRequestDTO));
        
    //     var saved = sectionService.save(examSave.getId(), sRequestDTO);
    //     assertNotNull(saved);

    //     Section sectionFromDB = entityManager.find(Section.class, saved.id());
    //     assertEquals(saved, sectionFromDB);

    //     QuestionExam questionExam =  sectionFromDB.getQuestionExams().iterator().next();
    //     assertEquals(question.getId(), questionExam.getQuestion().getId());

    //     var examFromDB = entityManager.find(Exam.class, examSave.getId());
    //     assertFalse(examFromDB.getSections().isEmpty());

    // }
}
