package pl.edu.agh.kis.Model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import pl.edu.agh.kis.Exception.InvalidRangeException;
import pl.edu.agh.kis.Model.question.QuestionClientSide;
import pl.edu.agh.kis.Model.question.QuestionServerSide;
import pl.edu.agh.kis.utils.RandomNumberWithRange;
import pl.edu.agh.kis.utils.RatespielGetPropertyValues;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Created by Karl on 11.01.2017.
 */
public class QuestionClientSideTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    String path = RatespielGetPropertyValues.getPath();
    /**
     * constant seed=2 makes first element swaps with second, after that again first with third
     * [0 1 2 3] original, after randomize [1 2 0 3]
     */
    private void checkDifferentOrderOfAnswersWithConstantSeed(int ID, String path) {
        QuestionClientSideTest questionClientSideTest = new QuestionClientSideTest();
        QuestionClientSideTest.QuestionClientSideTestImplementation testedQuestion = null;
        int seed = 2;
        try {
            QuestionServerSide tmp = new QuestionServerSide(ID, path);
            testedQuestion = questionClientSideTest.new QuestionClientSideTestImplementation(tmp.getAnswers(), tmp.getToTranslate(), seed);
            testedQuestion.randomizeAnswers();


            for (int i = 0; i < 4; i++) {
                if (i == 0) {
                    assertEquals(testedQuestion.answersBeforeRandomize.get(i), testedQuestion.getAnswers().get(i + 2));

                } else if (i == 1) {
                    assertEquals(testedQuestion.answersBeforeRandomize.get(i), testedQuestion.getAnswers().get(i - 1));

                } else if (i == 2) {
                    assertEquals(testedQuestion.answersBeforeRandomize.get(i), testedQuestion.getAnswers().get(i - 1));
                } else {
                    assertEquals(testedQuestion.answersBeforeRandomize.get(i), testedQuestion.getAnswers().get(i));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



    @Test(expected = IndexOutOfBoundsException.class)
    public void checkNonExistingQuestion() {
        checkDifferentOrderOfAnswersWithConstantSeed(-1, path);

    }



    class QuestionClientSideTestImplementation extends QuestionClientSide {
        int seed;
        ArrayList<String> answersBeforeRandomize;

        QuestionClientSideTestImplementation(ArrayList<String> answers, String toTranslate, int seed) throws FileNotFoundException {
            super(answers, toTranslate);
            this.seed = seed;
            this.answersBeforeRandomize = new ArrayList<String>(this.answers);
        }

        @Override
        public void randomizeAnswers() {
            System.out.println(answers);
            answers = durstenfeldShuffle(answers, seed);
            System.out.println(answers);

        }

        private ArrayList<String> durstenfeldShuffle(ArrayList<String> unsortedAnswers, int seed) {
            ArrayList<String> ret = new ArrayList<String>(unsortedAnswers);
            RandomNumberWithRange random = new RandomNumberWithRange(seed);

            for (int i = ret.size() - 1; i > 0; i--) {
                int j = 0;
                try {
                    j = random.randomInteger(0, i);
                } catch (InvalidRangeException e) {
                    e.printStackTrace();
                }
                Collections.swap(ret, j, i);
                System.out.println("j: " + j + " i: " + i);
            }

            return ret;
        }


    }

}