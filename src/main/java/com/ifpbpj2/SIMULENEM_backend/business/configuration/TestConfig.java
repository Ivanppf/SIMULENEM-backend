package com.ifpbpj2.SIMULENEM_backend.business.configuration;

import java.util.Arrays;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Alternative;
import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Category;
import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Illustration;
import com.ifpbpj2.SIMULENEM_backend.model.entities.question.Question;
import com.ifpbpj2.SIMULENEM_backend.model.enums.Difficulty;
import com.ifpbpj2.SIMULENEM_backend.model.enums.Origin;
import com.ifpbpj2.SIMULENEM_backend.model.enums.QuestionType;
import com.ifpbpj2.SIMULENEM_backend.model.repositories.question.CategoryRepository;
import com.ifpbpj2.SIMULENEM_backend.model.repositories.question.QuestionRepository;

@Configuration
public class TestConfig implements CommandLineRunner {

    QuestionRepository questionRepository;
    CategoryRepository categoryRepository;

    public TestConfig(QuestionRepository questionRepository, CategoryRepository categoryRepository) {
        this.questionRepository = questionRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // Categorias
        Category matematica = new Category("Matemática", Origin.AREA_CONHECIMENTO_ENEM);
        Category ciencias = new Category("Ciências", Origin.DISCIPLINA);

        categoryRepository.saveAll(Arrays.asList(matematica,ciencias));

        // Ilustrações
        Illustration ilustracaoPi = new Illustration("Imagem representando o número pi", "https://exemplo.com/pi.png");
        Illustration ilustracaoAgua = new Illustration("Molécula de água", "https://exemplo.com/h2o.png");
        Illustration ilustracaoNewton = new Illustration("Diagrama da primeira lei de Newton",
                "https://exemplo.com/newton1.png");

        // Alternativas
        Set<Alternative> alternativasQ1 = Set.of(
                new Alternative("3,14", null),
                new Alternative("2,71", null),
                new Alternative("1,61", null),
                new Alternative("3,41", null));

        Set<Alternative> alternativasQ2 = Set.of(
                new Alternative("Verdadeiro", null),
                new Alternative("Falso", null));

        Set<Alternative> alternativasQ3 = Set.of(
                new Alternative("A força resultante sobre um corpo é diretamente proporcional à sua aceleração", null),
                new Alternative(
                        "Todo corpo permanece em repouso ou em movimento retilíneo uniforme se nenhuma força atuar sobre ele",
                        null),
                new Alternative("A ação é sempre oposta à reação", null));

        // Questões
        Question q1 = new Question(
                QuestionType.FECHADA,
                "Qual é o valor de π (pi) arredondado para duas casas decimais?",
                ilustracaoPi,
                alternativasQ1,
                Set.of(matematica),
                Difficulty.FACIL,
                "3,14");

        Question q2 = new Question(
                QuestionType.FECHADA,
                "A água é composta por dois átomos de hidrogênio e um de oxigênio.",
                ilustracaoAgua,
                alternativasQ2,
                Set.of(ciencias),
                Difficulty.FACIL,
                "Verdadeiro");

        Question q3 = new Question(
                QuestionType.FECHADA,
                "Qual é a definição da primeira lei de Newton?",
                ilustracaoNewton,
                alternativasQ3,
                Set.of(ciencias, matematica),
                Difficulty.MEDIO,
                "Todo corpo permanece em repouso ou em movimento retilíneo uniforme se nenhuma força atuar sobre ele");

        questionRepository.saveAll(Arrays.asList(q1,q2,q3));

    }

}
