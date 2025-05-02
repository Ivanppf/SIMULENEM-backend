package com.ifpbpj2.SIMULENEM_backend.model.repositories;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
import com.ifpbpj2.SIMULENEM_backend.model.entities.Category;
import com.ifpbpj2.SIMULENEM_backend.model.entities.Illustration;
import com.ifpbpj2.SIMULENEM_backend.model.entities.Origin;
import com.ifpbpj2.SIMULENEM_backend.model.entities.Question;
import com.ifpbpj2.SIMULENEM_backend.model.enums.Difficulty;
import com.ifpbpj2.SIMULENEM_backend.model.enums.QuestionType;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TestEntityManager entityManager;

    List<Question> questions = new ArrayList<>();
    List<Category> categories = new ArrayList<>();

    @BeforeEach
    void setUp(){
       initializeQuestions();
       initializeCategories();
    }
    @AfterEach
    void tearDown(){
        entityManager.clear();
        questions.clear();
        categories.clear();
    }

    void initializeQuestions(){
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
        questions.add(entityManager.persist(question1));
        questions.add(entityManager.persist(question2));
        questions.add(entityManager.persist(question3));
    }
    void initializeCategories(){
        Category category1 = new Category("Geografia", Origin.DISCIPLINA);
        Category category2 = new Category("Matematica", Origin.DISCIPLINA);
        List<Question> qList = new ArrayList<>();
        qList.add(questions.get(0));
        qList.add(questions.get(2));
        category1.setQuestions(qList);
        categories.add(entityManager.persist(category1));
        categories.add(entityManager.persist(category2));
    }
    
    @Test
    void givenValidCategory_whenSave_thenPersist(){
        Category category = new Category("Matematica", Origin.DISCIPLINA);

        var categorySaved = categoryRepository.save(category);

        UUID id = (UUID) entityManager.getId(category);
        assertEquals(id, categorySaved.getId());
    }

    @Test
    void givenIdExists_whenFindById_thenReturnEntity(){
        UUID id = categories.get(0).getId();
        Optional<Category> category = categoryRepository.findById(id);
        assertTrue(category.isPresent());
        assertEquals(categories.get(0), category.get());
    }

    @Test
    void givenNonExistentId_whenFindById_thenReturnsEmptyOptional(){
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000000");
        Optional<Category> category = categoryRepository.findById(id);
        assertFalse(category.isPresent());
    }

    @Test
    void givenExistingEntity_whenDelete_thenEntityIsRemoved(){
        Category categoryExistent = categories.get(0);
        categoryRepository.delete(categoryExistent);
        Category category = entityManager.find(Category.class, categoryExistent.getId());
        assertNull(category);
    }

    @Test
    void givenExistingEntity_whenSave_thenEntityIsUpdate(){
        Category category = entityManager.find(Category.class, categories.get(0).getId());
        category.setName("Português");
        Category update = categoryRepository.save(category);
        assertEquals(category.getName(), update.getName());
        assertEquals(categories.size(),categoryRepository.count());
    }
}
