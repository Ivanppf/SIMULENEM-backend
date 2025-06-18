package com.ifpbpj2.SIMULENEM_backend.business.services.classgroup;

import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ifpbpj2.SIMULENEM_backend.business.services.exam.ExamService;
import com.ifpbpj2.SIMULENEM_backend.model.entities.classgroup.ClassGroup;
import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.Exam;
import com.ifpbpj2.SIMULENEM_backend.model.repositories.classgroup.ClassGroupRepository;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.ClassGroupRequestDTO;

@Service
public class ClassGroupService {

    private final ClassGroupRepository classGroupRepository;
    private final ExamService examService;

    public ClassGroupService(ClassGroupRepository classGroupRepository, ExamService examService) {
        this.classGroupRepository = classGroupRepository;
        this.examService = examService;
    }

    public ClassGroup findById(UUID id) {
        return classGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Turma com id " + id + " não encontrada"));
    }

    public ClassGroup save(ClassGroupRequestDTO obj) {

        Set<Exam> exams = examService.findAllById(obj.examIds());
        ClassGroup classGroup = new ClassGroup(obj);
        classGroup.setExams(exams);

        return classGroupRepository.save(classGroup);
    }

    public ClassGroup update(UUID id, ClassGroupRequestDTO obj) {
        findById(id);
        Set<Exam> exams = examService.findAllById(obj.examIds());
        ClassGroup classGroup = new ClassGroup(obj);
        classGroup.setExams(exams);
        classGroup.setId(id);
        return classGroupRepository.save(classGroup);
    }

    public void deleteById(UUID id) {
        findById(id);
        classGroupRepository.deleteById(id);
    }
    
}
