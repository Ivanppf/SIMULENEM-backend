package com.ifpbpj2.SIMULENEM_backend.tests.model.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.ifpbpj2.SIMULENEM_backend.factory.QuestionFactory;
import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Question;
import com.ifpbpj2.SIMULENEM_backend.model.repositories.question.QuestionRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class QuestionRepositoryTest {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    TestEntityManager entityManager;

    List<Question> questions = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Question question1 = QuestionFactory.newQuestionFechadaValid();
        Question question2 = QuestionFactory.newQuestionFechadaValid();
        Question question3 = QuestionFactory.newQuestionFechadaValid();
        questions.add(entityManager.persist(question1));
        questions.add(entityManager.persist(question2));
        questions.add(entityManager.persist(question3));
    }

    @AfterEach
    void tearDown() {
        entityManager.clear();
        questions.clear();
    }

    @Test
    void givenValidQuestion_whenSave_thenPersist() {
        Question question = QuestionFactory.newQuestionFechadaValid();
        Question questionSaved = questionRepository.save(question);
        assertNotNull(questionSaved.getId());
    }

    @Test
    void givenIdExists_whenFindById_thenReturnEntity() {
        Question questionExistent = questions.get(0);
        Optional<Question> question = questionRepository.findById(questionExistent.getId());
        assertTrue(question.isPresent());
        assertEquals(questionExistent, question.get());
    }

    @Test
    void givenNonExistentId_whenFindById_thenReturnsEmptyOptional() {
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000000");
        Optional<Question> question = questionRepository.findById(id);
        assertFalse(question.isPresent());
    }

    @Test
    void givenExistingEntity_whenDelete_thenEntityIsRemoved() {
        Question questionExistent = questions.get(0);
        questionRepository.delete(questionExistent);
        Question question = entityManager.find(Question.class, questionExistent.getId());
        assertNull(question);
    }

}
