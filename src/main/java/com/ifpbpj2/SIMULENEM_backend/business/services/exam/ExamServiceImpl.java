package com.ifpbpj2.SIMULENEM_backend.business.services.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.stream.Collectors;

import com.ifpbpj2.SIMULENEM_backend.infra.client.AnswerSheetClient;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.answerSheetClient.GabaritoResponseDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.answerSheetClient.GeracaoCartaoRespostaDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.answerSheetClient.QuestionExamDosCabaDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.answerSheetClient.SectionAnswerSheet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ifpbpj2.SIMULENEM_backend.infra.pdfGenerator.PdfGenerator;
import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.Exam;
import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.QuestionExam;
import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.Section;
import com.ifpbpj2.SIMULENEM_backend.model.repositories.exam.ExamRepository;
import com.ifpbpj2.SIMULENEM_backend.model.repositories.exam.projections.ExamProjection;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.request.ExamRequestDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.ExamResponseDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.ExamResponseDosCabaDTO;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.response.SectionResponseDosCabaDTO;

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

    public byte[] generatePdf(UUID id) {
        var exam = findById(id);
        return PdfGenerator.generateExamPdf(exam);
    }

    @Override
    @Transactional(readOnly = true)
    public ExamResponseDosCabaDTO getModelCard(UUID id) {
        var exam = findById(id);

        var sections = exam.getSections().stream().map(section -> {

            var totalQuestoes = section.getQuestionExams().size();
            var inicioQuestoes = section.getQuestionExams().iterator().next().getPosition();

            var positions = section.getQuestionExams().stream()
                    .map(qe -> qe.getPosition())
                    .collect(Collectors.toCollection(TreeSet::new));
            var fimQuestoes = positions.last();

            return new SectionResponseDosCabaDTO(
                    section.getTitle(),
                    totalQuestoes,
                    section.getTitleAbbreviation(),
                    inicioQuestoes,
                    fimQuestoes);

        }).toList();

        return new ExamResponseDosCabaDTO(exam.getId(), exam.getTitle(), exam.getNumberOfQuestions(), 5, sections);
    }

    @Override
    public GabaritoResponseDTO gerarGabarito(UUID id) {
        var exam = findById(id);

        List<QuestionExamDosCabaDTO> questionExamDosCaba = new ArrayList<>();
        for (Section s : exam.getSections()) {
            for (QuestionExam qE : s.getQuestionExams()) {
                questionExamDosCaba.add(new QuestionExamDosCabaDTO(qE));
            }
        }

        return new GabaritoResponseDTO(questionExamDosCaba);
    }

}
