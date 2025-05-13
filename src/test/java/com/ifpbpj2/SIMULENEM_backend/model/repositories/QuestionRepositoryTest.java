package com.ifpbpj2.SIMULENEM_backend.model.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.ifpbpj2.SIMULENEM_backend.model.entities.Alternative;
import com.ifpbpj2.SIMULENEM_backend.model.entities.Illustration;
import com.ifpbpj2.SIMULENEM_backend.model.entities.Question;
import com.ifpbpj2.SIMULENEM_backend.model.enums.Difficulty;
import com.ifpbpj2.SIMULENEM_backend.model.enums.QuestionType;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class QuestionRepositoryTest {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    TestEntityManager entityManager;

    List<Question> questions = new ArrayList<>();
    
    @BeforeEach
    void setUp(){
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
            alternatives01, Set.of(), Difficulty.MEDIO,"a"
        );

        Question question2 = new Question(
            QuestionType.FECHADA,
            "Which of the following are programming languages?",
            new Illustration("Snippet of code", "image5.test"),
            alternatives02, Set.of(), Difficulty.FACIL,"a,b"
        );

        Question question3 = new Question(
            QuestionType.FECHADA,
            "The Earth is flat.",
            new Illustration("Flat Earth illustration", "image9.test"),
            alternatives03, Set.of(), Difficulty.FACIL,"b"
        );

        questions.add(entityManager.persist(question1));
        questions.add(entityManager.persist(question2));
        questions.add(entityManager.persist(question3));
        
    }

    @AfterEach
    void tearDown(){
        entityManager.clear();
        questions.clear();
    }

    @Test
    void givenValidQuestion_whenSave_thenPersist(){
        Set<Alternative> alternatives = new HashSet<>();
        var alternative1 = new Alternative('a', "Mercury", new Illustration("Incorrect answer", "image12.test"));
        var alternative2 = new Alternative('b', "Venus", new Illustration("Incorrect answer", "image13.test"));
        var alternative3 = new Alternative('c', "Mars", new Illustration("Correct answer", "image14.test"));
        alternatives.addAll(Set.of(alternative1, alternative2, alternative3));
        Question question = new Question(QuestionType.FECHADA,"Which planet is known as the Red Planet?",
            new Illustration("An illustration of Mars", "image15.test"),
            alternatives, Set.of(), Difficulty.MEDIO, "c"
        );
        Question questionSaved = questionRepository.save(question);
        assertNotNull(questionSaved.getId());
    }

    @Test
    void givenIdExists_whenFindById_thenReturnEntity(){
        Question questionExistent = questions.get(0);
        Optional<Question> question = questionRepository.findById(questionExistent.getId());
        assertTrue(question.isPresent());
        assertEquals(questionExistent, question.get());
    }
    
    @Test
    void givenNonExistentId_whenFindById_thenReturnsEmptyOptional(){
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000000");
        Optional<Question> question = questionRepository.findById(id);
        assertFalse(question.isPresent());
    }

    @Test
    void givenExistingEntity_whenDelete_thenEntityIsRemoved(){
        Question questionExistent = questions.get(0);
        questionRepository.delete(questionExistent);
        Question question = entityManager.find(Question.class, questionExistent.getId());
        assertNull(question);
    }

}
