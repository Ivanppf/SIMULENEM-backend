package com.ifpbpj2.SIMULENEM_backend.infra.pdfGenerator;

import java.io.ByteArrayOutputStream;

import com.ifpbpj2.SIMULENEM_backend.exception.PdfGenerationException;
import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.Exam;
import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.QuestionExam;
import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.Section;
import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Alternative;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfGenerator {

        private static Font TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 28, Font.BOLD, BaseColor.WHITE);
        private static Font SUBTITLE_FONT = new Font(Font.FontFamily.HELVETICA, 16, Font.NORMAL, BaseColor.WHITE);
        private static Font SECTION_TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        private static Font NORMAL_FONT = new Font(Font.FontFamily.HELVETICA, 12);

        private PdfGenerator() {
        }

        public static byte[] generateExamPdf(Exam exam)
                        throws PdfGenerationException {

                try {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();

                        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
                        PdfWriter writer = PdfWriter.getInstance(document, baos);
                        document.open();

                        // ====== CAPA ======
                        PdfContentByte canvas = writer.getDirectContentUnder();
                        Rectangle rect = new Rectangle(0, 0, PageSize.A4.getWidth(), PageSize.A4.getHeight());
                        rect.setBackgroundColor(new BaseColor(30, 136, 229)); // Azul ENEM
                        canvas.rectangle(rect);

                        Paragraph title = new Paragraph(exam.getTitle(), TITLE_FONT);
                        title.setAlignment(Element.ALIGN_CENTER);
                        document.add(new Paragraph("\n\n\n\n"));
                        document.add(title);

                        Paragraph subtitle = new Paragraph("Simulado Enem - IFPB", SUBTITLE_FONT);
                        subtitle.setAlignment(Element.ALIGN_CENTER);
                        document.add(subtitle);

                        document.newPage();

                        // ====== INSTRUÇÕES ======
                        document.add(new Paragraph("Instruções", SECTION_TITLE_FONT));
                        document.add(new Paragraph("1. Verifique se este caderno contém todas as questões.\n"));
                        document.add(new Paragraph("2. Apenas uma alternativa é correta.\n"));
                        document.add(new Paragraph("3. Use caneta esferográfica preta de corpo transparente.\n"));
                        document.add(new Paragraph("4. A folha de redação está ao final do caderno."));
                        document.newPage();

                        // ====== QUESTÕES ======
                        Font questionFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
                        Font altFont = new Font(Font.FontFamily.HELVETICA, 12);

                        int num = 1;
                        for (Section s : exam.getSections()) {
                                var questionExams = s.getQuestionExams();
                                for (QuestionExam qE : questionExams) {
                                        var question = qE.getQuestion();
                                        document.add(new Paragraph(num + ") " + question.getTitle(), questionFont));
                                        char letter = 'A';

                                        for (Alternative alt : question.getAlternatives()) {
                                                document.add(new Paragraph("   " + letter + ") " + alt.getText(),
                                                                altFont));
                                                letter++;
                                        }
                                        document.add(Chunk.NEWLINE);
                                        num++;
                                }
                        }

                        // ====== FOLHA DE REDAÇÃO ======
                        document.newPage();
                        document.add(new Paragraph("Folha de Redação", SECTION_TITLE_FONT));
                        document.add(new Paragraph(
                                        "\nUtilize o espaço abaixo para escrever sua redação. Lembre-se de utilizar caneta esferográfica preta e respeitar o tema proposto.",
                                        NORMAL_FONT));
                        document.add(Chunk.NEWLINE);

                        PdfPTable table = new PdfPTable(1);
                        table.setWidthPercentage(100);

                        for (int i = 0; i < 25; i++) {
                                var emptyCell = new PdfPCell();
                                emptyCell.setFixedHeight(20);
                                table.addCell(emptyCell);
                        }

                        table.setSpacingBefore(15);

                        document.add(table);

                        document.close();
                        return baos.toByteArray();

                } catch (Exception e) {
                        throw new PdfGenerationException("Error generating PDF", e);
                }
        }
}
