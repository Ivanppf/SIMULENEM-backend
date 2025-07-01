package com.ifpbpj2.SIMULENEM_backend.model.repositories.question;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Question;
import com.ifpbpj2.SIMULENEM_backend.model.repositories.question.projections.QuestionProjection;

public interface QuestionRepository extends JpaRepository<Question, UUID> {
        @Query("SELECT q FROM Question q")
        Page<QuestionProjection> findAllPageable(Pageable pageable);

}
