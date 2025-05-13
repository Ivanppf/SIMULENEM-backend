package com.ifpbpj2.SIMULENEM_backend.business.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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

import com.ifpbpj2.SIMULENEM_backend.model.entities.Category;
import com.ifpbpj2.SIMULENEM_backend.model.entities.Question;
import com.ifpbpj2.SIMULENEM_backend.model.enums.Difficulty;
import com.ifpbpj2.SIMULENEM_backend.model.enums.Origin;
import com.ifpbpj2.SIMULENEM_backend.model.enums.QuestionType;
import com.ifpbpj2.SIMULENEM_backend.model.repositories.QuestionRepository;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.AlternativesRequestDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.IllustrationRequestDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.QuestionRequestDTO;

@SpringBootTest
class QuestionServiceImplTest {

    @MockitoBean
    QuestionRepository questionRepository;

    @MockitoBean
    CategoryService categoryService;

    @Autowired
    QuestionService questionService;

    static List<Question> questions = new ArrayList<>();


    @BeforeAll
    static void setUp(){
        initializeQuestions();
    }

    static void initializeQuestions(){
        QuestionRequestDTO questionRequest1 = new QuestionRequestDTO(
            QuestionType.ABERTA,
            "What is the capital of France?",
            new IllustrationRequestDTO("A map of Paris", "image1.test"),
            Set.of(
            new AlternativesRequestDTO('a', "Paris", new IllustrationRequestDTO("Correct answer", "image2.test")),
            new AlternativesRequestDTO('b', "London", new IllustrationRequestDTO("Incorrect answer", "image3.test")),
            new AlternativesRequestDTO('c', "Berlin", new IllustrationRequestDTO("Incorrect answer", "image4.test"))
            ),
            Set.of(),
            Difficulty.FACIL,
            "a"
        );

        QuestionRequestDTO questionRequest2 = new QuestionRequestDTO(
            QuestionType.FECHADA,
            "Which of the following are programming languages?",
            new IllustrationRequestDTO("Snippet of code", "image5.test"),
            Set.of(
            new AlternativesRequestDTO('a', "Java", new IllustrationRequestDTO("Correct answer", "image6.test")),
            new AlternativesRequestDTO('b', "Python", new IllustrationRequestDTO("Correct answer", "image7.test")),
            new AlternativesRequestDTO('c', "HTML", new IllustrationRequestDTO("Incorrect answer", "image8.test"))
            ),
            Set.of(),
            Difficulty.FACIL,
            "a,b"
        );

        QuestionRequestDTO questionRequest3 = new QuestionRequestDTO(
            QuestionType.FECHADA,
            "The Earth is flat.",
            new IllustrationRequestDTO("Flat Earth illustration", "image9.test"),
            Set.of(
            new AlternativesRequestDTO('a', "True", new IllustrationRequestDTO("Incorrect answer", "image10.test")),
            new AlternativesRequestDTO('b', "False", new IllustrationRequestDTO("Correct answer", "image11.test"))
            ),
            Set.of(),
            Difficulty.FACIL,
            "b"
        );

        Question question1 = new Question(questionRequest1);
        Question question2 = new Question(questionRequest2);
        Question question3 = new Question(questionRequest3);

        questions.add(question1);
        questions.add(question2);
        questions.add(question3);
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
        Category category = new Category("Portugues", Origin.DISCIPLINA);
        Set<String> categorias = Set.of(category.getName());
        when(questionRepository.save(questionExpected)).thenReturn(questionExpected);
        when(categoryService.findByNameIn(categorias)).thenReturn(Set.of(category));

        Question questionResult = questionService.save(questionExpected, categorias);
        assertEquals(questionExpected, questionResult);
        assertFalse(questionResult.getCategories().isEmpty());
        verify(questionRepository).save(questionExpected);
        verify(categoryService).findByNameIn(categorias);
    }

    @Test
    void givenExistingId_whenUpdate_thenUpdateEntity() {
        Question question = questions.get(0);
        question.setId(UUID.randomUUID());
        QuestionRequestDTO dto = new QuestionRequestDTO(
            QuestionType.FECHADA,
            "Updated question title",
            new IllustrationRequestDTO(
            "Updated illustration description",
            "updated_image.test"
            ),
            Set.of(
            new AlternativesRequestDTO(
                'b',
                "Updated alternative text",
                new IllustrationRequestDTO("Updated alternative illustration description", "updated_alt_image.test")
            )
            ),
            Set.of("Portugues"),
            Difficulty.DIFICIL,
            "b"
        );
        Question questioUpdate = new Question(dto);
        when(questionRepository.existsById(question.getId())).thenReturn(true);
        when(questionRepository.save(questioUpdate)).thenReturn(questioUpdate);
        Question result = questionService.update(question.getId(), questioUpdate);
        assertEquals(question.getId(), result.getId());
        verify(questionRepository).existsById(question.getId());
        verify(questionRepository).save(questioUpdate);
    }
}
