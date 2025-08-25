package com.ifpbpj2.SIMULENEM_backend.model.repositories.question;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Question;
import com.ifpbpj2.SIMULENEM_backend.model.enums.Difficulty;
import com.ifpbpj2.SIMULENEM_backend.model.enums.QuestionType;

public interface QuestionRepository extends JpaRepository<Question, UUID> {

  @Query("""
          SELECT q FROM Question q
          JOIN q.categories c
          WHERE (:questionType IS NULL OR q.questionType = :questionType)
            AND (:title IS NULL OR LOWER(q.title) LIKE LOWER(CONCAT('%', :title, '%')))
            AND (:difficulty IS NULL OR q.difficulty = :difficulty)
            AND (:lastUsedDate IS NULL OR q.lastUsedDate >= :lastUsedDate)
            AND (:categoryNames IS NULL OR LOWER(c.name) IN :categoryNames)
          GROUP BY q
      """)
  Page<Question> findByFilters(
      @Param("questionType") QuestionType questionType,
      @Param("title") String title,
      @Param("categoryNames") Set<String> categoryNames,
      @Param("difficulty") Difficulty difficulty,
      @Param("lastUsedDate") LocalDateTime lastUsedDate,
      Pageable pageable);

}
