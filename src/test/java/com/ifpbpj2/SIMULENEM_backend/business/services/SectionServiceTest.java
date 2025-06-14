package com.ifpbpj2.SIMULENEM_backend.business.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.ifpbpj2.SIMULENEM_backend.business.services.exam.ExamService;
import com.ifpbpj2.SIMULENEM_backend.business.services.exam.SectionService;
import com.ifpbpj2.SIMULENEM_backend.business.services.question.QuestionService;
import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.Exam;
import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.QuestionExam;
import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.Section;
import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Question;
import com.ifpbpj2.SIMULENEM_backend.model.repositories.SectionRepository;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.QuestionExamRequestDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.SectionRequestDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.SectionResponseDTO;

@SpringBootTest
public class SectionServiceTest {

    @MockitoBean
    SectionRepository sectionRepository;
    @MockitoBean
    ExamService examService;
    @MockitoBean
    QuestionService questionService;
    SectionService sectionService;

    @BeforeEach
    void setUp() {
        sectionService = new SectionService(sectionRepository, examService, questionService);
    }

    @Test
    @Order(1)
    void testSave() {
        QuestionExamRequestDTO questionExamRequestDTO1 = new QuestionExamRequestDTO(1, 1D, UUID.randomUUID());
        SectionRequestDTO sectionRequestDTO = new SectionRequestDTO(1, "titulo1", Set.of(questionExamRequestDTO1));
        Exam exam = new Exam();
        Question question = new Question();
        Section section = new Section();
        section.setQuestionExams(Set.of(new QuestionExam()));

        when(examService.findById(any(UUID.class))).thenReturn(exam);
        when(questionService.findById(any(UUID.class))).thenReturn(question);
        when(sectionRepository.save(any(Section.class))).thenReturn(section);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        SectionResponseDTO returnedSection = sectionService.save(UUID.randomUUID(), sectionRequestDTO);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        assertNotNull(returnedSection);

        // Exam exam = new Exam();
        // Section section = new Section();
        // section.setPosition(1);
        // section.setTitle("titulo1");
        // when(examService.findById(any(UUID.class))).thenReturn(exam);
        // when(sectionRepository.save(any(Section.class))).thenReturn(section);

        // SectionResponseDTO returnedSection = sectionService.save(UUID.randomUUID(),
        // sectionRequestDTO);

        // assertNotNull(returnedSection);
        // assertEquals(section, returnedSection);
        // assertEquals(section.getPosition(), returnedSection.position());
        // assertEquals(section.getTitle(), returnedSection.title());

    }

    @Test
    void testUpdate() {

    }

    @Test
    void testFindById() {
        Section section = new Section();
        section.setExam(new Exam());
        section.setPosition(1);
        section.setTitle("titulo1");
        when(sectionRepository.findById(any(UUID.class))).thenReturn(Optional.of(section));
        Section returnedSection = sectionService.findById(UUID.randomUUID());
        assertNotNull(returnedSection);
        assertNotNull(returnedSection.getExam());
        assertEquals(section, returnedSection);
        verify(sectionRepository, times(1)).findById(any(UUID.class));

    }

    @Test
    void findSectionsByExamId() {
        Exam exam = new Exam();
        Section sec1 = new Section(1, "titulo1");
        Section sec2 = new Section(2, "titulo2");
        Set<Section> sections = Set.of(sec1, sec2);
        exam.setSections(sections);
        when(examService.findById(any(UUID.class))).thenReturn(exam);
        List<SectionResponseDTO> returnedSections = sectionService.findSectionsByExamId(UUID.randomUUID());
        assertNotNull(returnedSections);
        assertEquals(sec1.getPosition(), returnedSections.get(0).position());
        assertEquals(sec1.getTitle(), returnedSections.get(0).title());
        assertEquals(2, returnedSections.size());

    }

    @Test
    void deleteById() {
        when(sectionRepository.findById(any(UUID.class))).thenReturn(Optional.of(new Section()));
        doNothing().when(sectionRepository).delete(any(Section.class));

        sectionService.deleteById(UUID.randomUUID());

        verify(sectionRepository, times(1)).findById(any(UUID.class));
        verify(sectionRepository, times(1)).delete(any(Section.class));

    }

}
