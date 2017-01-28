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
        for (int i = 0; i < answers.size(); i++) {
            this.answers.add(new String(answers.get(i)));
        }
    }

    public QuestionClientSideAbstract() {

    }

    public static void main(String[] args) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("a");
        arrayList.add("b");
        arrayList.add("c");
        arrayList.add("d");

        QuestionClientSide questionClientSide = new QuestionClientSide(arrayList, "");

        for (int i = 0; i < 4; i++) {
            System.out.println(questionClientSide.answers.get(i));
        }

        questionClientSide.randomizeAnswers();
        System.out.println();

        for (int i = 0; i < 4; i++) {
            System.out.println(questionClientSide.answers.get(i));
        }
    }

    public void randomizeAnswers() {
        ArrayList<String> ret = new ArrayList<>(getAnswers());
        ArrayList<String> tmp = durstenfeldShuffle(ret);
        setAnswers(tmp);
    }

    protected ArrayList<String> durstenfeldShuffle(ArrayList<String> unsortedAnswers) {
        ArrayList<String> ret = new ArrayList<>(unsortedAnswers);
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
