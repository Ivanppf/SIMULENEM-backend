package com.ifpbpj2.SIMULENEM_backend.tests.model.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

import com.ifpbpj2.SIMULENEM_backend.factory.CategoryFactory;
import com.ifpbpj2.SIMULENEM_backend.factory.QuestionFactory;
import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Category;
import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Question;
import com.ifpbpj2.SIMULENEM_backend.model.enums.Origin;
import com.ifpbpj2.SIMULENEM_backend.model.repositories.question.CategoryRepository;

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
    void setUp() {
        initializeQuestions();
        initializeCategories();
    }

    @AfterEach
    void tearDown() {
        entityManager.clear();
        questions.clear();
        categories.clear();
    }

    void initializeQuestions() {
        Question question1 = QuestionFactory.newQuestionFechadaValid();
        Question question2 = QuestionFactory.newQuestionFechadaValid();
        Question question3 = QuestionFactory.newQuestionFechadaValid();
        questions.add(entityManager.persist(question1));
        questions.add(entityManager.persist(question2));
        questions.add(entityManager.persist(question3));
    }

    void initializeCategories() {
        Category category1 = CategoryFactory.newCategoryValid();
        Category category2 = CategoryFactory.newCategoryValid();
        List<Question> qList = new ArrayList<>();
        qList.add(questions.get(0));
        qList.add(questions.get(2));
        category1.setQuestions(qList);
        categories.add(entityManager.persist(category1));
        categories.add(entityManager.persist(category2));
    }

    @Test
    void givenValidCategory_whenSave_thenPersist() {
        Category category = new Category("Matematica", Origin.DISCIPLINA);

        var categorySaved = categoryRepository.save(category);

        UUID id = (UUID) entityManager.getId(category);
        assertEquals(id, categorySaved.getId());
    }

    @Test
    void givenIdExists_whenFindById_thenReturnEntity() {
        UUID id = categories.get(0).getId();
        Optional<Category> category = categoryRepository.findById(id);
        assertTrue(category.isPresent());
        assertEquals(categories.get(0), category.get());
    }

    @Test
    void givenNonExistentId_whenFindById_thenReturnsEmptyOptional() {
        UUID id = UUID.fromString("00000000-0000-0000-0000-000000000000");
        Optional<Category> category = categoryRepository.findById(id);
        assertFalse(category.isPresent());
    }

    @Test
    void givenExistingEntity_whenDelete_thenEntityIsRemoved() {
        Category categoryExistent = categories.get(0);
        categoryRepository.delete(categoryExistent);
        Category category = entityManager.find(Category.class, categoryExistent.getId());
        assertNull(category);
    }

    @Test
    void givenExistingEntity_whenSave_thenEntityIsUpdate() {
        Category categoryExistent = entityManager.persist(CategoryFactory.newCategoryValid());
        Category category = entityManager.find(Category.class, categoryExistent.getId());
        category.setName("Português");
        Category update = categoryRepository.save(category);
        assertEquals(categoryExistent.getName(), update.getName());
    }
}
