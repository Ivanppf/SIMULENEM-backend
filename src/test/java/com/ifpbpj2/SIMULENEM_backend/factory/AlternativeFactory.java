package com.ifpbpj2.SIMULENEM_backend.factory;

import com.github.javafaker.Faker;
import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Alternative;

public class AlternativeFactory {

    private final static Faker faker = new Faker();

    public static Alternative newAlternativeWithIllustracion() {
        Alternative alternative = new Alternative();
        alternative.setText(faker.lorem().sentence());
        alternative.setIllustration(IllustrationFactory.newIllustrationValid());
        return alternative;
    }

    public static Alternative newAlternative() {
        Alternative alternative = new Alternative();
        alternative.setText(faker.lorem().sentence());
        return alternative;
    }

}
