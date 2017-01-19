package pl.edu.agh.kis.Model.question;

import java.util.Vector;

/**
 * Created by Karl on 06.01.2017.
 */
public class QuestionClientSide extends QuestionClientSideAbstract {
    private String toTranslate;

    public QuestionClientSide(Vector<String> answers, String toTranslate) {
        super(answers);
        this.toTranslate = toTranslate;
    }

    public QuestionClientSide(QuestionServerSide question) {
        super(question.getAnswers());
        this.toTranslate = question.getToTranslate();
    }

    public String getToTranslate() {
        return toTranslate;
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
