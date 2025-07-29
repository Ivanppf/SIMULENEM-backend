package com.ifpbpj2.SIMULENEM_backend.factory;

import java.util.HashSet;
import java.util.Set;

import com.github.javafaker.Faker;
import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Alternative;
import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Category;
import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Question;
import com.ifpbpj2.SIMULENEM_backend.model.enums.Difficulty;
import com.ifpbpj2.SIMULENEM_backend.model.enums.QuestionType;

public class QuestionFactory {

    private final static Faker faker = new Faker();

    public static Question newQuestionAbertaValid() {
        Question question = new Question();
        question.setTitle(faker.lorem().paragraph());
        question.setQuestionType(QuestionType.ABERTA);
        question.setDifficulty(Difficulty.fromInt(faker.number().numberBetween(1, 4)));
        question.setExpectedAnswer(faker.lorem().sentence());
        question.setCategories(newSetCategories());
        question.setIllustration(IllustrationFactory.newIllustrationValid());
        return question;
    }

    public static Question newQuestionFechadaValid() {
        Question question = new Question();
        question.setTitle(faker.lorem().paragraph());
        question.setQuestionType(QuestionType.FECHADA);
        question.setDifficulty(Difficulty.fromInt(faker.number().numberBetween(1, 4)));
        question.setAlternatives(newSetAlternativesValid());
        String correctAlternative = question.getAlternatives().stream().findFirst().get().getText();
        question.setExpectedAnswer(correctAlternative);
        question.setCategories(newSetCategories());
        question.setIllustration(IllustrationFactory.newIllustrationValid());
        return question;
    }

    private static Set<Category> newSetCategories() {
        Set<Category> categories = new HashSet<>();
        categories.add(CategoryFactory.newCategoryValid());
        categories.add(CategoryFactory.newCategoryValid());
        categories.add(CategoryFactory.newCategoryValid());
        return categories;
    }

    private static Set<Alternative> newSetAlternativesValid() {
        Set<Alternative> alternatives = new HashSet<>();
        alternatives.add(AlternativeFactory.newAlternative());
        alternatives.add(AlternativeFactory.newAlternative());
        alternatives.add(AlternativeFactory.newAlternative());
        alternatives.add(AlternativeFactory.newAlternative());
        return alternatives;
    }

}
