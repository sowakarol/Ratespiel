package pl.edu.agh.kis.Model.question;

import pl.edu.agh.kis.Exception.InvalidRangeException;
import pl.edu.agh.kis.utils.RandomNumberWithRange;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Karl on 15.01.2017.
 */
public class QuestionClientSideAbstract extends Question {

    public QuestionClientSideAbstract(ArrayList<String> answers) {
        this.answers = answers;
    }


    public void randomizeAnswers() {
        ArrayList<String> ret = new ArrayList<>(this.getAnswers());
        ret = durstenfeldShuffle(getAnswers());
        setAnswers(ret);
    }


    protected ArrayList<String> durstenfeldShuffle(ArrayList<String> unsortedAnswers) {
        ArrayList<String> ret = new ArrayList<>(unsortedAnswers);
        RandomNumberWithRange random = new RandomNumberWithRange();
        System.out.println(ret);

        for (int i = ret.size() - 1; i > 0; i--) {
            int j = 0;
            try {
                j = random.randomInteger(0, i);
            } catch (InvalidRangeException e) {
                e.printStackTrace();
            }
            Collections.swap(ret, j, i);
        }
        System.out.println(ret);

        return ret;
    }
}
