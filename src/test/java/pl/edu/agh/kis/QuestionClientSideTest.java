package pl.edu.agh.kis;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Karl on 07.01.2017.
 */
class QuestionClientSideTest {
    public static void main(String[] args) throws FileNotFoundException {
        QuestionClientSideTest questionClientSideTest = new QuestionClientSideTest();
        QuestionClientSideTest.QuestionClientSideTestImplementation questionClientSideTestImplementation = questionClientSideTest.new QuestionClientSideTestImplementation(2, 2);

        questionClientSideTestImplementation.randomizeAnswers();
        System.out.println(questionClientSideTestImplementation.answers);

    }

    /**
     * constant seed=2 makes first element swap with second
     */
    @Test
    public void checkDifferentOrderOfAnswersWithConstantSeed() {
        QuestionClientSideTest questionClientSideTest = new QuestionClientSideTest();
        QuestionClientSideTestImplementation testedQuestion = null;
        int testID = 2;
        try {
            testedQuestion = questionClientSideTest.new QuestionClientSideTestImplementation(testID, 2);
            testedQuestion.randomizeAnswers();


            for (int i = 0; i < 4; i++) {
                if (i == 0) {
                    assertEquals(testedQuestion.answersBeforRandomize.get(i), testedQuestion.answers.get(i + 1));

                } else if (i == 1) {
                    assertEquals(testedQuestion.answersBeforRandomize.get(i), testedQuestion.answers.get(i - 1));

                } else {
                    assertEquals(testedQuestion.answersBeforRandomize.get(i), testedQuestion.answers.get(i));
                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    class QuestionClientSideTestImplementation extends QuestionClientSide {
        int seed;
        Vector<String> answersBeforRandomize;

        QuestionClientSideTestImplementation(int id, int seed) throws FileNotFoundException {
            super(id);
            this.seed = seed;
            this.answersBeforRandomize = new Vector<>(this.answers);
        }

        @Override
        public void randomizeAnswers() {
            answers = durstenfeldShuffle(answers, seed);

        }

        private Vector<String> durstenfeldShuffle(Vector<String> unsortedAnswers, int seed) {
            Vector<String> ret = new Vector<>(unsortedAnswers);
            RandomNumberWithRange random = new RandomNumberWithRange(seed);

            for (int i = ret.size() - 1; i > 0; i--) {
                int j = random.randomInteger(0, i);
                Collections.swap(ret, j, i);
            }

            return ret;
        }


    }


}