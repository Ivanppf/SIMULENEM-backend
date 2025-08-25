package com.ifpbpj2.SIMULENEM_backend.factory;

import com.github.javafaker.Faker;
import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Illustration;

public class IllustrationFactory {

    private final static Faker faker = new Faker();

    public static Illustration newIllustrationValid() {
        Illustration illustration = new Illustration();
        illustration.setUrl(faker.internet().url());
        illustration.setDescription(faker.lorem().sentence());
        return illustration;
    }

}
