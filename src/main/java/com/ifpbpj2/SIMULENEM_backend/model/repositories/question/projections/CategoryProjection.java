package com.ifpbpj2.SIMULENEM_backend.model.repositories.question.projections;

import java.util.UUID;

import com.ifpbpj2.SIMULENEM_backend.model.enums.Origin;

public interface CategoryProjection {

        UUID getId();

        String getName();

        Origin getOrigin();

}
