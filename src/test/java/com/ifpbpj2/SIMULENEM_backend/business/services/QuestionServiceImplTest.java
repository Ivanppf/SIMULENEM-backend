package com.ifpbpj2.SIMULENEM_backend.business.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.ifpbpj2.SIMULENEM_backend.model.entities.Alternative;
import com.ifpbpj2.SIMULENEM_backend.model.entities.Illustration;
import com.ifpbpj2.SIMULENEM_backend.model.entities.Question;
import com.ifpbpj2.SIMULENEM_backend.model.enums.Difficulty;
import com.ifpbpj2.SIMULENEM_backend.model.enums.QuestionType;
import com.ifpbpj2.SIMULENEM_backend.model.repositories.QuestionRepository;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.QuestionRequestDTO;

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
    void givenValidQuestionFilter_whenFind_thenReturnMatchingEntities() {
        Question questionFilter = new Question();
        questionFilter.setQuestionType(QuestionType.FECHADA);
        List<Question> questionsExpected = new ArrayList<>(List.of(questions.get(1), questions.get(2)));
        when(questionRepository.findAll(any(Example.class))).thenReturn(questionsExpected);

        List<Question> questionsResult = questionService.find(questionFilter);
        assertEquals(questionsExpected, questionsResult);
        verify(questionRepository).findAll(any(Example.class));
    }

    @Test
    void givenInvalidQuestionFilter_whenFind_thenReturnEmptyList(){
        Question questionFilter = new Question();
        questionFilter.setTitle("test question filter invalid");
        List<Question> questionsExpected = new ArrayList<>();
        when(questionRepository.findAll(any(Example.class))).thenReturn(questionsExpected);

        List<Question> questionsResult = questionService.find(questionFilter);
        assertEquals(questionsExpected, questionsResult);
        verify(questionRepository).findAll(any(Example.class));

    }

    @Test
    void givenExistingId_whenFindById_thenReturnEntity() {
        Question question = questions.get(0);
        UUID id = question.getId();
        when(questionRepository.findById(id)).thenReturn(Optional.of(question));
        Question result = questionService.findById(id);
        assertEquals(question, result);
        verify(questionRepository).findById(id); 
    }

    @Test
    void givenNoExistingId_whenFindById_thenThrowsRuntimeException(){
        UUID id = UUID.randomUUID();
        when(questionRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> questionService.findById(id));
        verify(questionRepository).findById(id);
    }

    @Test
    void givenValidQuestion_whenSave_thenReturnEntity() {
        Question questionExpected = questions.get(0);
        when(questionRepository.save(questionExpected)).thenReturn(questionExpected);
        Question questionResult = questionService.save(questionExpected);
        assertEquals(questionExpected, questionResult);
        verify(questionRepository).save(questionExpected);
    }

    @Test
    void givenExistingId_whenUpdate_thenUpdateEntity() {
        Question question = questions.get(0);
        question.setId(UUID.randomUUID());
        QuestionRequestDTO dto = new QuestionRequestDTO(QuestionType.FECHADA,
            "Updated question title",
            new Illustration("Updated illustration description", "updated_image.test"),  
            Set.of(new Alternative('a', "Updated alternative", new Illustration("Updated alt description", "alt_image.test"))),
            Difficulty.MEDIO,
            "a");
        Question questioUpdate = new Question(dto);
        when(questionRepository.existsById(question.getId())).thenReturn(true);
        when(questionRepository.save(questioUpdate)).thenReturn(questioUpdate);
        Question result = questionService.update(question.getId(), questioUpdate);
        assertEquals(question.getId(), result.getId());
        verify(questionRepository).existsById(question.getId());
        verify(questionRepository).save(questioUpdate);
    }
}
