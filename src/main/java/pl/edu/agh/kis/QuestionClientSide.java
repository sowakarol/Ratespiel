package pl.edu.agh.kis;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Vector;

/**
 * Created by Karl on 06.01.2017.
 */
public class QuestionClientSide extends Question {
    QuestionClientSide(int id) {
        super(id);
    }

    public QuestionClientSide(Vector<String> answers, String trueAnswer) {
        super(answers, trueAnswer);
    }

    public static void main(String[] args) {

        QuestionClientSide questionClientSide = new QuestionClientSide(2);
        questionClientSide.randomizeAnswers();
        System.out.println("" + questionClientSide);



    }

    public void randomizeAnswers() {
        answers = durstenfeldShuffle(answers);
    }

    private Vector<String> durstenfeldShuffle(Vector<String> unsortedAnswers) {
        Vector<String> ret = new Vector<>(unsortedAnswers);
        RandomNumberWithRange random = new RandomNumberWithRange();
        System.out.println(ret);

        for (int i = ret.size() - 1; i > 0; i--) {
            int j = random.randomInteger(0, i);
            Collections.swap(ret, j, i);
        }
        System.out.println(ret);

        return ret;
    }
}
