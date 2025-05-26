package com.ifpbpj2.SIMULENEM_backend.business.services.exam;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ifpbpj2.SIMULENEM_backend.business.services.exceptions.SectionNotFoundException;
import com.ifpbpj2.SIMULENEM_backend.business.services.question.QuestionService;
import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.Exam;
import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.QuestionExam;
import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.Section;
import com.ifpbpj2.SIMULENEM_backend.model.repositories.SectionRepository;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.QuestionExamRequestDTO;
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
    public SectionResponseDTO save(UUID examUuid, SectionRequestDTO requestDTO){
        Exam exam = examService.findById(examUuid);
        Set<QuestionExam> questionExams = fromQuestionExams(requestDTO.questions());
        Section section = new Section(requestDTO.position(), requestDTO.title(), questionExams);
        exam.getSections().add(section);
        return fromSectionResponseDTO(sectionRepository.save(section));    
    }
    private SectionResponseDTO fromSectionResponseDTO(Section section){
        List<QuestionExamResponseDTO> questionExamResponse = section.getQuestions()
            .stream().map(QuestionExamResponseDTO::new).toList();
        return new SectionResponseDTO(section, questionExamResponse);
    }
    private Set<QuestionExam> fromQuestionExams(Set<QuestionExamRequestDTO> dtos){
        return dtos.stream()
            .map(q -> {
                var question = questionService.findById(q.questionUuid());
                return new QuestionExam(q, question);
            }).collect(Collectors.toSet());
    }


    public SectionResponseDTO update(UUID uuidExam, UUID uuidSection, SectionRequestDTO requestDTO){
        Exam exam = examService.findById(uuidExam);
        Section section = findById(uuidSection);
        Set<QuestionExam> questionExams = fromQuestionExams(requestDTO.questions());
        Section sectionUpdate = new Section(requestDTO.position(), requestDTO.title(), questionExams);
        sectionUpdate.setExam(exam);
        sectionUpdate.setId(section.getId());
        return fromSectionResponseDTO(sectionRepository.save(sectionUpdate));
    }

    public Section findById(UUID sectionUuid) {
        return sectionRepository.findById(sectionUuid)
            .orElseThrow(() -> new SectionNotFoundException(sectionUuid));
    }

    public List<SectionResponseDTO> findAll(UUID examUuid){
        Exam exam = examService.findById(examUuid);
        return exam.getSections().stream()
            .map(this::fromSectionResponseDTO).toList();
    }
}
