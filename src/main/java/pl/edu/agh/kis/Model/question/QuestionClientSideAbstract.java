package pl.edu.agh.kis.Model.question;

import pl.edu.agh.kis.Exception.InvalidRangeException;
import pl.edu.agh.kis.utils.RandomNumberWithRange;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Karl on 15.01.2017.
 * Abstract class of question client side
 */
public class QuestionClientSideAbstract extends Question {

    /**
     * @param answers
     */
    public QuestionClientSideAbstract(ArrayList<String> answers) {
        for (int i = 0; i < answers.size(); i++) {
            this.answers.add(answers.get(i));
        }
    }

    /**
     * Non-parameter constructor
     */
    public QuestionClientSideAbstract() {
    }


    /**
     * method randomizing answers by dursenfeld shuffle
     */
    public void randomizeAnswers() {
        ArrayList<String> ret = new ArrayList<String>(getAnswers());
        ArrayList<String> tmp = durstenfeldShuffle(ret);
        setAnswers(tmp);
    }

    /**
     * @param unsortedAnswers not sorted answers to be random sorted
     * @return ArrayList of random sorted @param unsortedAnswers
     */
    protected ArrayList<String> durstenfeldShuffle(ArrayList<String> unsortedAnswers) {
        ArrayList<String> ret = new ArrayList<String>(unsortedAnswers);
        RandomNumberWithRange random = new RandomNumberWithRange();
        //System.out.println(ret);

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
