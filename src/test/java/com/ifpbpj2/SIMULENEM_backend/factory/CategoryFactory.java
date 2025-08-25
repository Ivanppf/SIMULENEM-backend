package com.ifpbpj2.SIMULENEM_backend.factory;

import java.util.UUID;

import com.github.javafaker.Faker;
import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Category;
import com.ifpbpj2.SIMULENEM_backend.model.enums.Origin;

public class CategoryFactory {

    private final static Faker faker = new Faker();

    public static Category newCategoryValid() {
        Category category = new Category();
        category.setId(UUID.randomUUID());
        category.setName(faker.lorem().word());
        category.setOrigin(Origin.fromInt(faker.number().numberBetween(1, 4)));
        return category;
    }

}
