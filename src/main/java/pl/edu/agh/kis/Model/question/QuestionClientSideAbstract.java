package pl.edu.agh.kis.Model.question;

import pl.edu.agh.kis.utils.RandomNumberWithRange;

import java.util.Collections;
import java.util.Vector;

/**
 * Created by Karl on 15.01.2017.
 */
public class QuestionClientSideAbstract extends Question {

    public QuestionClientSideAbstract(Vector<String> answers) {
        this.answers = answers;
    }


    public void randomizeAnswers() {
        setAnswers(durstenfeldShuffle(getAnswers()));
    }


    protected Vector<String> durstenfeldShuffle(Vector<String> unsortedAnswers) {
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
