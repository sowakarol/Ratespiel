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
        int testID = 2;
        int seed = 2;
        QuestionServerSide tmp = new QuestionServerSide(testID);

        QuestionClientSideTest.QuestionClientSideTestImplementation questionClientSideTestImplementation = questionClientSideTest.new QuestionClientSideTestImplementation(tmp.answers, tmp.toTranslate, seed);

        questionClientSideTestImplementation.randomizeAnswers();
        System.out.println(questionClientSideTestImplementation.answers);
        System.out.println(questionClientSideTestImplementation.answersBeforRandomize);

    }

    /**
     * constant seed=2 makes first element swaps with second, after that again first with third
     * [0 1 2 3] original, after randomize [1 2 0 3]
     */
    private void checkDifferentOrderOfAnswersWithConstantSeed(int ID) {
        QuestionClientSideTest questionClientSideTest = new QuestionClientSideTest();
        QuestionClientSideTestImplementation testedQuestion = null;
        int seed = 2;
        try {
            QuestionServerSide tmp = new QuestionServerSide(ID);
            testedQuestion = questionClientSideTest.new QuestionClientSideTestImplementation(tmp.answers, tmp.toTranslate, seed);
            testedQuestion.randomizeAnswers();


            for (int i = 0; i < 4; i++) {
                if (i == 0) {
                    assertEquals(testedQuestion.answersBeforRandomize.get(i), testedQuestion.answers.get(i + 2));

                } else if (i == 1) {
                    assertEquals(testedQuestion.answersBeforRandomize.get(i), testedQuestion.answers.get(i - 1));

                } else if (i == 2) {
                    assertEquals(testedQuestion.answersBeforRandomize.get(i), testedQuestion.answers.get(i - 1));
                } else {
                    assertEquals(testedQuestion.answersBeforRandomize.get(i), testedQuestion.answers.get(i));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkDifferentOrderOfAnswersWithConstantSeedForIDequals2() {
        checkDifferentOrderOfAnswersWithConstantSeed(2);
    }

    @Test
    public void checkDifferentOrderOfAnswersWithConstantSeedForIDequals3() {
        checkDifferentOrderOfAnswersWithConstantSeed(3);
    }

    class QuestionClientSideTestImplementation extends QuestionClientSide {
        int seed;
        Vector<String> answersBeforRandomize;

        QuestionClientSideTestImplementation(Vector<String> answers, String toTranslate, int seed) throws FileNotFoundException {
            super(answers, toTranslate);
            this.seed = seed;
            this.answersBeforRandomize = new Vector<>(this.answers);
        }

        @Override
        public void randomizeAnswers() {
            System.out.println(answers);
            answers = durstenfeldShuffle(answers, seed);
            System.out.println(answers);

        }

        private Vector<String> durstenfeldShuffle(Vector<String> unsortedAnswers, int seed) {
            Vector<String> ret = new Vector<>(unsortedAnswers);
            RandomNumberWithRange random = new RandomNumberWithRange(seed);

            for (int i = ret.size() - 1; i > 0; i--) {
                int j = random.randomInteger(0, i);
                Collections.swap(ret, j, i);
                System.out.println("j: " + j + " i: " + i);
            }

            return ret;
        }


    }


}