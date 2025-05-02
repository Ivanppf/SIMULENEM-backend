package com.ifpbpj2.SIMULENEM_backend.business.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.ifpbpj2.SIMULENEM_backend.model.entities.Alternative;
import com.ifpbpj2.SIMULENEM_backend.model.entities.Illustration;
import com.ifpbpj2.SIMULENEM_backend.model.entities.Question;
import com.ifpbpj2.SIMULENEM_backend.model.enums.Difficulty;
import com.ifpbpj2.SIMULENEM_backend.model.enums.QuestionType;
import com.ifpbpj2.SIMULENEM_backend.model.repositories.QuestionRepository;

@SpringBootTest
class QuestionServiceImplTest {

    @MockitoBean
    QuestionRepository questionRepository;

    @Autowired
    QuestionService questionService;

    static List<Question> questions = new ArrayList<>();


    @BeforeAll
    static void setUp(){
        initializeQuestions();
    }

    static void initializeQuestions(){
        Set<Alternative> alternatives01 = new HashSet<>();
        Alternative alternative01a = new Alternative('a', "Paris", new Illustration( "Correct answer", "image2.test"));
        Alternative alternative01b = new Alternative('b', "London", new Illustration("Incorrect answer", "image3.test"));
        Alternative alternative01c = new Alternative('c', "Berlin", new Illustration("Incorrect answer", "image4.test"));
        alternatives01.addAll(Set.of(alternative01a, alternative01b, alternative01c));

        Set<Alternative> alternatives02 = new HashSet<>();
        Alternative alternative02a = new Alternative('a', "Java", new Illustration("Correct answer", "image6.test"));
        Alternative alternative02b = new Alternative('b', "Python", new Illustration("Correct answer", "image7.test"));
        Alternative alternative02c = new Alternative('c', "HTML", new Illustration("Incorrect answer", "image8.test"));
        alternatives02.addAll(Set.of(alternative02a, alternative02b, alternative02c));

        Set<Alternative> alternatives03 = new HashSet<>();
        Alternative alternative03a = new Alternative('a', "True", new Illustration("Incorrect answer", "image10.test"));
        Alternative alternative03b = new Alternative('b', "False", new Illustration("Correct answer", "image11.test"));
        alternatives03.addAll(Set.of(alternative03a, alternative03b));

        Question question1 = new Question(
            QuestionType.ABERTA,
            "What is the capital of France?",
            new Illustration("A map of Paris", "image1.test"),
            alternatives01, Difficulty.FACIL,"a"
        );

        Question question2 = new Question(
            QuestionType.FECHADA,
            "Which of the following are programming languages?",
            new Illustration("Snippet of code", "image5.test"),
            alternatives02, Difficulty.FACIL,"a,b"
        );

        Question question3 = new Question(
            QuestionType.FECHADA,
            "The Earth is flat.",
            new Illustration("Flat Earth illustration", "image9.test"),
            alternatives03, Difficulty.FACIL,"b"
        );
        questions.add(question1);
        questions.add(question2);
        questions.add(question3);
    }

    @Test
    void givenExistingId_whenDeleteById_thenRemoveEntity() {
        UUID id = UUID.randomUUID();
        doNothing().when(questionRepository).deleteById(id);
        when(questionRepository.existsById(id)).thenReturn(true);
        questionService.deleteById(id);
        verify(questionRepository).existsById(id);
        verify(questionRepository).deleteById(id);
    }
    @Test
    void givenNoExistingId_whenDeleteById_thenThrowsRunTimeException(){
        UUID id = UUID.randomUUID();
        doNothing().when(questionRepository).deleteById(id);
        when(questionRepository.existsById(id)).thenReturn(false);
        assertThrows(RuntimeException.class, 
                        () -> questionService.deleteById(id));
        verify(questionRepository).existsById(id);
        verify(questionRepository, never()).deleteById(id);
        }

    @Test
    void givenExistingId_whenExistsById_thenDoesNotThrowException() {
        UUID id = UUID.randomUUID();
        when(questionRepository.existsById(id)).thenReturn(true);
        questionService.existsById(id);
        verify(questionRepository).existsById(id);
    }
    @Test
    void givenNoExistingId_whenExistsById_thenThrowsRunTimeException(){
        UUID id = UUID.randomUUID();
        when(questionRepository.existsById(id)).thenReturn(false);
        assertThrows(RuntimeException.class, 
            () -> questionService.existsById(id));
        verify(questionRepository).existsById(id);
    }

    @Test
    void testFind() {

    }

    @Test
    void testFindById() {

    }

    @Test
    void testSave() {

    }

    @Test
    void testUpdate() {

    }
}
