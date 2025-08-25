// package com.ifpbpj2.SIMULENEM_backend.tests.business.services;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyLong;
// import static org.mockito.Mockito.doNothing;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// import java.time.LocalDate;
// import java.util.List;
// import java.util.Optional;
// import java.util.UUID;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.data.domain.Example;
// import org.springframework.data.domain.ExampleMatcher;
// import org.springframework.data.domain.PageImpl;
// import org.springframework.data.domain.Pageable;
// import org.springframework.test.context.bean.override.mockito.MockitoBean;

// import com.ifpbpj2.SIMULENEM_backend.business.services.exam.ExamService;
// import com.ifpbpj2.SIMULENEM_backend.business.services.exam.ExamServiceImpl;
// import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.Exam;
// import com.ifpbpj2.SIMULENEM_backend.model.repositories.exam.ExamRepository;
// import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.ExamRequestDTO;
// import
// com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.ExamResponseDTO;

// @SpringBootTest
// public class ExamServiceImplTest {

// @MockitoBean
// ExamRepository examRepository;
// ExamService examService;
// @MockitoBean
// Pageable pageable;
// @MockitoBean
// Example example;

// @BeforeEach
// public void setUp() {
// examService = new ExamServiceImpl(examRepository);
// }

// @Test
// public void testeDeveEncontrarTodos() {
// var exams = List.of(new Exam("exam 1", LocalDate.parse("2023-10-01"), true),
// new Exam("exam 2", LocalDate.parse("2023-10-02"), false));
// PageImpl<Exam> pageImpl = new PageImpl<>(exams);
// when(examRepository.findAll(any(Example.class),
// any(Pageable.class))).thenReturn(pageImpl);

// var foundExams = examService.findAll(pageable, new Exam());
// var examsResponseDTO = List.of(new ExamResponseDTO(exams.get(0), null),
// new ExamResponseDTO(exams.get(1), null));

// var examsResponseDTOPage = new PageImpl<>(examsResponseDTO);
// assertEquals(examsResponseDTOPage, foundExams);
// assertEquals("exam 1", examsResponseDTOPage.getContent().get(0).title());
// assertEquals("exam 2", examsResponseDTOPage.getContent().get(1).title());
// }

// @Test
// public void testeDeveEncontrarPorId() {
// Exam exam = new Exam("exam 1", LocalDate.parse("2023-10-01"), true);
// when(examRepository.findById(any(UUID.class)))
// .thenReturn(Optional.of(exam));
// var returnedExam = examService.findById(UUID.randomUUID());

// assertNotNull(returnedExam);
// assertEquals("exam 1", returnedExam.getTitle());
// assertEquals(exam, returnedExam);
// verify(examRepository, times(1)).findById(any(UUID.class));

// }

// @Test
// public void testeDeveSalvar() {
// Exam exam = new Exam("exam 1", LocalDate.parse("2023-10-01"), true);
// when(examRepository.save(any(Exam.class))).thenReturn(exam);
// var returnedExam = examService.save(exam);

// var examResponseDTO = new ExamResponseDTO(exam, null);

// assertNotNull(returnedExam);
// assertEquals("exam 1", returnedExam.title());
// assertEquals(returnedExam, examResponseDTO);
// verify(examRepository, times(1)).save(any(Exam.class));

// }

// @Test
// public void testeDeveAtualizar() {
// var oldExamDTO = new ExamRequestDTO("exam 1", LocalDate.parse("2023-10-01"),
// true);
// var oldExam = new Exam("exam 1", LocalDate.parse("2023-10-01"), true);
// var newExam = new Exam("exam 1", LocalDate.parse("2023-10-15"), true);

// when(examRepository.findById(any(UUID.class))).thenReturn(Optional.of(oldExam));
// when(examRepository.save(any(Exam.class))).thenReturn(newExam);

// var returnedExam = examService.update(UUID.randomUUID(), oldExamDTO);

// assertNotNull(returnedExam);
// assertEquals("exam 1", returnedExam.title());
// assertEquals(LocalDate.parse("2023-10-15"), returnedExam.applicationDate());
// assertEquals(true, returnedExam.resultPublished());
// verify(examRepository, times(1)).findById(any(UUID.class));
// verify(examRepository, times(1)).save(any(Exam.class));

// }

// @Test
// public void testeDeveDeletarPorId() {
// when(examRepository.findById(any(UUID.class))).thenReturn(Optional.of(new
// Exam()));
// doNothing().when(examRepository).deleteById(any(UUID.class));

// examService.deleteById(UUID.randomUUID());

// verify(examRepository, times(1)).deleteById(any(UUID.class));
// }

// }
