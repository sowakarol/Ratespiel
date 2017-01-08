package pl.edu.agh.kis;

import java.util.Collections;
import java.util.Vector;

/**
 * Created by Karl on 06.01.2017.
 */
public class QuestionClientSide extends Question {
    private String toTranslate;

    public QuestionClientSide(Vector<String> answers, String toTranslate) {
        super(answers);
        this.toTranslate = toTranslate;
    }

    public String getToTranslate() {
        return toTranslate;
    }

    public void randomizeAnswers() {
        setAnswers(durstenfeldShuffle(getAnswers()));
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

    @Override
    public String toString() {
        String ret = "toTranslate: " + toTranslate + "\n";
        for (int i = 0; i < 4; i++) {
            ret += "" + i + " " + answers.get(i) + "\n";
        }
        return ret;
    }
}
