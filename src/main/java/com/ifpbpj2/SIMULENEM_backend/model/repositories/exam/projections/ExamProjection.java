package com.ifpbpj2.SIMULENEM_backend.model.repositories.exam.projections;

import java.time.LocalDate;
import java.util.UUID;

public interface ExamProjection {

        UUID getId();

        String getTitle();

        LocalDate getApplicationDate();

        boolean isResultPublished();

}
