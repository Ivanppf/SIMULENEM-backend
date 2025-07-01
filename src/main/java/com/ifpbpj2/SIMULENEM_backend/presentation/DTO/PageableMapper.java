package com.ifpbpj2.SIMULENEM_backend.presentation.DTO;

import org.springframework.data.domain.Page;

public class PageableMapper {

        private PageableMapper() {
        }

        public static <T> PageableDTO<T> toDTO(Page<T> page) {
                return new PageableDTO<T>(page.getContent(), page.isFirst(), page.isLast(), page.getNumber(),
                                page.getSize(), page.getNumberOfElements(), page.getTotalPages(),
                                page.getTotalElements());
        }

}
