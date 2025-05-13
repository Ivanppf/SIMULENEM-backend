package com.ifpbpj2.SIMULENEM_backend.business.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.ifpbpj2.SIMULENEM_backend.business.services.exceptions.CategoryNotFoundException;
import com.ifpbpj2.SIMULENEM_backend.business.services.exceptions.EntityInUseException;
import com.ifpbpj2.SIMULENEM_backend.model.entities.Category;
import com.ifpbpj2.SIMULENEM_backend.model.entities.Question;
import com.ifpbpj2.SIMULENEM_backend.model.enums.Difficulty;
import com.ifpbpj2.SIMULENEM_backend.model.enums.Origin;
import com.ifpbpj2.SIMULENEM_backend.model.enums.QuestionType;
import com.ifpbpj2.SIMULENEM_backend.model.repositories.CategoryRepository;

@SpringBootTest
class CategoryServiceImplTest {

    @MockitoBean
    CategoryRepository categoryRepository;
    
    @Autowired
    CategoryService categoryService;

    @Test
    void givenCategoryWithoutQuestions_whenDeleteById_thenRemoveEntity() {
        Category category = new Category("Artes", Origin.DISCIPLINA);
        category.setId(UUID.randomUUID());
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        doNothing().when(categoryRepository).delete(category);
        categoryService.deleteById(category.getId());
        verify(categoryRepository).findById(category.getId());
        verify(categoryRepository).delete(category);
    }
    @Test
    void givenCategoryWithQuestions_whenDeleteById_thenThrowRunTimeException() {
        Category category = new Category("Matemática", Origin.DISCIPLINA);
        category.setId(UUID.randomUUID());
        Question question = new Question(QuestionType.ABERTA, "Qual é o resultado de 2 + 2?",
         null, null, null, Difficulty.FACIL, "4");
        question.setId(UUID.randomUUID());
        Set<Category> categories = new HashSet<>(Set.of(category));
        question.setCategories(categories);
        category.setQuestions(List.of(question));

        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));

        EntityInUseException error = assertThrowsExactly(EntityInUseException.class, () -> categoryService.deleteById(category.getId()));

        assertEquals("Ainda há questões cadastradas com essa categoria", error.getMessage());  
        verify(categoryRepository).findById(category.getId());
    }

    @Test
    void givenNoExistingId_whenDeleteById_thenThrowsRunTimeException(){
        UUID id = UUID.randomUUID();
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());
        assertThrowsExactly(CategoryNotFoundException.class, 
            () -> categoryService.deleteById(id)); 
        verify(categoryRepository).findById(id);
    }
    

    @Test
    void givenExistingId_whenExistsById_thenDoNotThrowException() {
        UUID id = UUID.randomUUID();
        when(categoryRepository.existsById(id)).thenReturn(true);
        assertDoesNotThrow(() -> categoryService.existsById(id));
        verify(categoryRepository).existsById(id);
    }
    @Test
    void givenNonExistingId_whenExistsById_thenThrowCategoryNotFoundException() {
        UUID id = UUID.randomUUID();
        when(categoryRepository.existsById(id)).thenReturn(false);
        assertThrowsExactly(CategoryNotFoundException.class,() -> categoryService.existsById(id));
        verify(categoryRepository).existsById(id);
    }

  @Test
    void givenValidCategoryFilter_whenFind_thenReturnMatchingEntities() {
        Category categoryFilter = new Category();
        categoryFilter.setName("Inglês");
        List<Category> categoryExpected = new ArrayList<>(List.of(new Category("Inglês", Origin.DISCIPLINA)));
        when(categoryRepository.findAll(any(Example.class))).thenReturn(categoryExpected);

        List<Category> categoryResult = categoryService.find(categoryFilter);
        assertEquals(categoryExpected, categoryResult);
        verify(categoryRepository).findAll(any(Example.class));
    }

    @Test
    void givenInvalidQuestionFilter_whenFind_thenReturnEmptyList(){
        Category categoryFilter = new Category();
        categoryFilter.setName("Teste");
        List<Category> categoryExpected = new ArrayList<>();
        when(categoryRepository.findAll(any(Example.class))).thenReturn(categoryExpected);

        List<Category> categoryResult = categoryService.find(categoryFilter);
        assertEquals(categoryExpected, categoryResult);
        verify(categoryRepository).findAll(any(Example.class));
    }

    @Test
    void givenExistingId_whenFindById_thenReturnCategory() {
        Category category = new Category("Artes", Origin.DISCIPLINA);
        category.setId(UUID.randomUUID());
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));

        Category categoryResult = categoryService.findById(category.getId());
        assertEquals(category, categoryResult);
        verify(categoryRepository).findById(category.getId());
    }
    @Test
    void givenNoExistingId_whenFindById_thenThrowsCategoryNotFoundException() {
        UUID id = UUID.randomUUID();
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        assertThrowsExactly(CategoryNotFoundException.class, () -> categoryService.findById(id));
        
        verify(categoryRepository).findById(id);
    }

    @Test
    void givenValidCategoryNames_whenFindByNameIn_thenReturnCategories() {
        List<String> names = new ArrayList<>(List.of("Matemática", "Física", "Química"));
        List<Category> categories = new ArrayList<>(List.of(
            new Category(names.get(0), Origin.DISCIPLINA),
            new Category(names.get(1), Origin.DISCIPLINA),
            new Category(names.get(2), Origin.DISCIPLINA)));
        when(categoryRepository.findByNameIn(new HashSet<>(names))).thenReturn(categories);

        Set<Category> categoriesResult = categoryService.findByNameIn(new HashSet<>(names));
        assertFalse(categoriesResult.isEmpty());
        verify(categoryRepository).findByNameIn(new HashSet<>(names));
    }
    @Test
    void givenNonExistingCategoryNames_whenFindByNameIn_thenThrowCategoryNotFoundException() {
        List<String> names = new ArrayList<>(List.of("Matemática", "Física", "Química"));
        when(categoryRepository.findByNameIn(new HashSet<>(names))).thenReturn(new ArrayList<>());

        assertThrowsExactly(CategoryNotFoundException.class, () -> categoryService.findByNameIn(new HashSet<>(names)));

        verify(categoryRepository).findByNameIn(new HashSet<>(names));
    }

    @Test
    void givenCategory_whenSave_thenReturnEntity() {
        Category category = new Category("História", Origin.DISCIPLINA);
        category.setId(UUID.randomUUID());
        when(categoryRepository.save(category)).thenReturn(category);
        Category categoryResult = categoryService.save(category);
        assertEquals(category, categoryResult);
        verify(categoryRepository).save(category);
    }

    @Test
    void testUpdate() {
        Category category = new Category("Matemática", Origin.DISCIPLINA);
        category.setId(UUID.randomUUID());
        Question question = new Question(QuestionType.ABERTA, "Qual é o resultado de 2 + 2?",
         null, null, null, Difficulty.FACIL, "4");
        question.setId(UUID.randomUUID());
        Set<Category> categories = new HashSet<>(Set.of(category));
        question.setCategories(categories);
        category.setQuestions(List.of(question));
        Category categoryUpdate = new Category("Soma", Origin.ASSUNTO);
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        when(categoryRepository.save(categoryUpdate)).thenAnswer(invoker -> invoker.getArgument(0));
        Category categoryResult = categoryService.update(category.getId(), categoryUpdate);
        assertEquals(category.getQuestions(), categoryResult.getQuestions());
        assertEquals(category.getId(), categoryResult.getId());
        assertEquals(categoryUpdate.getName(), categoryResult.getName());
        assertEquals(categoryUpdate.getOrigin(), categoryResult.getOrigin());

        verify(categoryRepository).findById(category.getId());
        verify(categoryRepository).save(categoryUpdate);
    }
}
