package com.ifpbpj2.SIMULENEM_backend.model.repositories.classgroup;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifpbpj2.SIMULENEM_backend.model.entities.classgroup.ClassGroup;

public interface ClassGroupRepository extends JpaRepository<ClassGroup, UUID> {
    
}
