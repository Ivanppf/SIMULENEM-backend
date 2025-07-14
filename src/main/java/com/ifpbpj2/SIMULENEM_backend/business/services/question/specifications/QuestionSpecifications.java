package com.ifpbpj2.SIMULENEM_backend.business.services.question.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Question;
import com.ifpbpj2.SIMULENEM_backend.presentation.DTO.queryParameters.QuestionQueryParametersDTO;

import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

public class QuestionSpecifications {

        public static Specification<Question> withFilters(QuestionQueryParametersDTO params) {
                return (root, query, cb) -> {
                        List<Predicate> predicates = new ArrayList<>();

                        if (params.getId() != null) {
                                predicates.add(cb.equal(root.get("id"), params.getId()));
                        }

                        if (params.getTitle() != null && !params.getTitle().isBlank()) {
                                predicates.add(cb.like(cb.lower(root.get("text")),
                                                "%" + params.getTitle().toLowerCase() + "%"));
                        }

                        if (params.getDifficulty() != null) {
                                predicates.add(cb.equal(root.get("difficulty"), params.getDifficulty()));
                        }

                        if (params.getQuestionType() != null) {
                                predicates.add(cb.equal(root.get("questionType"), params.getQuestionType()));
                        }

                        if (params.getCategoryNames() != null && !params.getCategoryNames().isEmpty()) {
                                predicates.add(root.get("category").get("name").in(params.getCategoryNames()));
                        }

                        if (params.getLastUsedDate() != null) {
                                predicates.add(cb.greaterThanOrEqualTo(root.get("lastUsedDate"),
                                                params.getLastUsedDate()));
                        }

                        return cb.and(predicates.toArray(new Predicate[0]));
                };
        }
}
