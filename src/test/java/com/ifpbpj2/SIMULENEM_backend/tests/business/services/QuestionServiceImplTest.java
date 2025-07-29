package com.ifpbpj2.SIMULENEM_backend.tests.business.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.ifpbpj2.SIMULENEM_backend.business.services.question.CategoryService;
import com.ifpbpj2.SIMULENEM_backend.business.services.question.QuestionService;
import com.ifpbpj2.SIMULENEM_backend.factory.QuestionFactory;
import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Category;
import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Question;
import com.ifpbpj2.SIMULENEM_backend.model.enums.Origin;
import com.ifpbpj2.SIMULENEM_backend.model.enums.QuestionType;
import com.ifpbpj2.SIMULENEM_backend.model.repositories.question.QuestionRepository;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.queryParameters.QuestionQueryParamsDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.CategoryResponseDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.QuestionResponseDTO;

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
    static void setUp() {
        initializeQuestions();
    }

    static void initializeQuestions() {
        Question question1 = QuestionFactory.newQuestionFechadaValid();
        Question question2 = QuestionFactory.newQuestionFechadaValid();
        Question question3 = QuestionFactory.newQuestionFechadaValid();

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
    void givenNoExistingId_whenDeleteById_thenThrowsRunTimeException() {
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
    void givenNoExistingId_whenExistsById_thenThrowsRunTimeException() {
        UUID id = UUID.randomUUID();
        when(questionRepository.existsById(id)).thenReturn(false);

        assertThrows(RuntimeException.class,
                () -> questionService.existsById(id));

        verify(questionRepository).existsById(id);
    }

    @Test
    void givenValidQuestionFilter_whenFind_thenReturnMatchingEntities() {
        Page<Question> questionPage = new PageImpl<>(List.of(questions.get(1), questions.get(2)));
        QuestionQueryParamsDTO filter = new QuestionQueryParamsDTO(QuestionType.FECHADA, null,
                null, null, null);
        Pageable pageable = PageRequest.of(0, 10);
        when(questionRepository.findByFilters(
                QuestionType.FECHADA,
                null,
                null,
                null,
                null,
                pageable))
                .thenReturn(questionPage);

        Page<QuestionResponseDTO> result = questionService
                .findAll(pageable, filter);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());

        verify(questionRepository).findByFilters(QuestionType.FECHADA,
                null,
                null,
                null,
                null,
                pageable);
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
    void givenNoExistingId_whenFindById_thenThrowsRuntimeException() {
        UUID id = UUID.randomUUID();
        when(questionRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> questionService.findById(id));
        verify(questionRepository).findById(id);
    }

    @Test
    void givenValidQuestion_whenSave_thenReturnEntity() {
        Question questionExpected = questions.get(0);
        Category category = new Category("Portugues", Origin.DISCIPLINA);
        CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getOrigin());
        Set<CategoryResponseDTO> dto = Set.of(categoryResponseDTO);
        when(questionRepository.save(questionExpected)).thenReturn(questionExpected);

        Question result = questionService.save(questionExpected, dto);

        assertEquals(questionExpected, result);
        assertFalse(result.getCategories().isEmpty());
        verify(questionRepository).save(questionExpected);
    }

    // @Test
    // void givenExistingId_whenUpdate_thenUpdateEntity() {
    // Question question = questions.get(0);
    // question.setId(UUID.randomUUID());
    // QuestionRequestDTO dto = new QuestionRequestDTO(
    // QuestionType.FECHADA,
    // "Updated question title",
    // new IllustrationRequestDTO(
    // "Updated illustration description",
    // "updated_image.test"
    // ),
    // Set.of(
    // new AlternativesRequestDTO(
    // 'b',
    // "Updated alternative text",
    // new IllustrationRequestDTO("Updated alternative illustration description",
    // "updated_alt_image.test")
    // )
    // ),
    // Set.of("Portugues"),
    // Difficulty.DIFICIL,
    // "b"
    // );
    // Question questioUpdate = new Question(dto);
    // when(questionRepository.existsById(question.getId())).thenReturn(true);
    // when(questionRepository.save(questioUpdate)).thenReturn(questioUpdate);
    // Question result = questionService.update(question.getId(), questioUpdate);
    // assertEquals(question.getId(), result.getId());
    // verify(questionRepository).existsById(question.getId());
    // verify(questionRepository).save(questioUpdate);
    // }
}
