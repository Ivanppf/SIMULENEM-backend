package com.ifpbpj2.SIMULENEM_backend.model.repositories.question;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Question;

public interface QuestionRepository extends JpaRepository<Question, UUID>{}
