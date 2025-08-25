package com.ifpbpj2.SIMULENEM_backend.business.services.exam;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ifpbpj2.SIMULENEM_backend.business.services.question.QuestionService;
import com.ifpbpj2.SIMULENEM_backend.exception.SectionNotFoundException;
import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.Exam;
import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.QuestionExam;
import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.Section;
import com.ifpbpj2.SIMULENEM_backend.model.repositories.exam.SectionRepository;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.AddQuestionsDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.SectionRequestDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.QuestionExamResponseDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.SectionResponseDTO;

import jakarta.transaction.Transactional;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;
    private final ExamService examService;
    private final QuestionService questionService;

    public SectionService(SectionRepository sectionRepository, ExamService examService,
            QuestionService questionService) {
        this.sectionRepository = sectionRepository;
        this.examService = examService;
        this.questionService = questionService;
    }

    @Transactional
    public SectionResponseDTO save(UUID examUuid, SectionRequestDTO requestDTO) {
        Exam exam = examService.findById(examUuid);
        Section section = new Section(requestDTO.position(), requestDTO.title());
        exam.addSection(section);
        return fromSectionResponseDTO(sectionRepository.save(section));
    }

    private SectionResponseDTO fromSectionResponseDTO(Section section) {
        List<QuestionExamResponseDTO> questionExamResponse = section.getQuestionExams()
                .stream().map(QuestionExamResponseDTO::new).toList();
        return new SectionResponseDTO(section, questionExamResponse);
    }

    public SectionResponseDTO update(UUID uuidExam, UUID uuidSection, SectionRequestDTO requestDTO) {
        Exam exam = examService.findById(uuidExam);
        Section section = findById(uuidSection);
        Section sectionUpdate = new Section(requestDTO.position(), requestDTO.title());
        sectionUpdate.setExam(exam);
        sectionUpdate.setId(section.getId());
        return fromSectionResponseDTO(sectionRepository.save(sectionUpdate));
    }

    public Section findById(UUID sectionUuid) {
        return sectionRepository.findById(sectionUuid)
                .orElseThrow(() -> new SectionNotFoundException(sectionUuid));
    }

    public List<SectionResponseDTO> findSectionsByExamId(UUID examUuid) {
        Exam exam = examService.findById(examUuid);
        return exam.getSections().stream()
                .map(this::fromSectionResponseDTO).toList();
    }

    public void deleteById(UUID sectionUuid) {
        Section section = findById(sectionUuid);
        sectionRepository.delete(section);
    }

    @Transactional
    public void addQuestions(UUID id, AddQuestionsDTO request) {
        var section = findById(id);

        var questions = request.questions().stream().map(questionExam -> {
            var question = questionService.findById(questionExam.questionUuid());
            return new QuestionExam(questionExam, question);
        }).collect(Collectors.toSet());

        section.addQuestions(questions);
    }

}
