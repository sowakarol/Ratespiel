package pl.edu.agh.kis;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Vector;

/**
 * Created by Karl on 06.01.2017.
 */
public class QuestionClientSide extends Question {
    QuestionClientSide(int id) throws FileNotFoundException {
        super(id);
    }

    public void randomizeAnswers() {
        answers = durstenfeldShuffle(answers);
    }

    private Vector<String> durstenfeldShuffle(Vector<String> unsortedAnswers) {
        Vector<String> ret = new Vector<>(unsortedAnswers);
        RandomNumberWithRange random = new RandomNumberWithRange();

        for (int i = ret.size() - 1; i >= 1; i++) {
            int j = random.randomInteger(0, i);
            Collections.swap(ret, j, i);
        }

        return ret;
    }
}
