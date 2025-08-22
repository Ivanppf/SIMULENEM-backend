package com.ifpbpj2.SIMULENEM_backend.business.services.exam;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.ifpbpj2.SIMULENEM_backend.infra.client.AnswerSheetClient;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.answerSheetClient.GeracaoCartaoRespostaDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.answerSheetClient.SectionAnswerSheet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.Exam;
import com.ifpbpj2.SIMULENEM_backend.model.repositories.exam.ExamRepository;
import com.ifpbpj2.SIMULENEM_backend.model.repositories.exam.projections.ExamProjection;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.ExamRequestDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.ExamResponseDTO;

@Service
public class ExamServiceImpl implements ExamService {

    private ExamRepository examRepository;
    private AnswerSheetClient answerSheetClient;

    public ExamServiceImpl(ExamRepository examRepository, AnswerSheetClient answerSheetClient) {
        this.examRepository = examRepository;
        this.answerSheetClient = answerSheetClient;
    }

    @Transactional(readOnly = true)
    public Page<ExamProjection> findAll(Pageable pageable) {
        return examRepository.findAllPageable(pageable);
    }

    @Override
    public Set<Exam> findAllById(Set<UUID> ids) {
        return examRepository.findAllById(ids).stream().collect(Collectors.toSet());
    }

    @Override
    public Exam findById(UUID id) {
        return examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prova com id " + id + " não encontrada"));
    }

    @Override
    public ExamResponseDTO save(Exam exam) {
        return new ExamResponseDTO(examRepository.save(exam));
    }

    @Override
    public ExamResponseDTO update(UUID id, ExamRequestDTO examUpdate) {
        Exam exam = findById(id);
        exam.setTitle(examUpdate.title());
        exam.setApplicationDate(examUpdate.applicationDate());
        exam.setResultPublished(examUpdate.resultPublished());
        return new ExamResponseDTO(examRepository.save(exam));
    }

    private GeracaoCartaoRespostaDTO generatedDtoCartaoResposta(UUID idExam) {
        Exam exam = findById(idExam);
        GeracaoCartaoRespostaDTO cartaoResposta = new GeracaoCartaoRespostaDTO();
        cartaoResposta.setIdProva(exam.getId());
        cartaoResposta.setTituloSimulado(exam.getTitle());
        cartaoResposta.setNumeroAlternativas(5);
        cartaoResposta.setNumeroTotalQuestoes(exam.getNumberOfQuestions());
        List<SectionAnswerSheet> sections = exam.getSections().stream()
                .map(SectionAnswerSheet::new).toList();
        cartaoResposta.setSecoes(sections);

        return cartaoResposta;
    }

    public Byte[] gerarCartaoResposta(UUID idExam) {
        GeracaoCartaoRespostaDTO cartaoRespostaDto = generatedDtoCartaoResposta(idExam);
        ResponseEntity<Byte[]> cartaoRespostaBytes = answerSheetClient.generatedAnswerSheet(cartaoRespostaDto);
        return cartaoRespostaBytes.getBody();
    }

    @Override
    public void deleteById(UUID id) {
        findById(id);
        examRepository.deleteById(id);
    }

}
