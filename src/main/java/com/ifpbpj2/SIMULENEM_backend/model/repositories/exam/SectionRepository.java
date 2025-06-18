package com.ifpbpj2.SIMULENEM_backend.model.repositories.exam;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifpbpj2.SIMULENEM_backend.model.entities.exam.Section;

public interface SectionRepository extends JpaRepository<Section, UUID>{}
