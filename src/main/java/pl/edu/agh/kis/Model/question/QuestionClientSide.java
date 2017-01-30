package pl.edu.agh.kis.Model.question;

import java.util.ArrayList;

/**
 * Created by Karl on 06.01.2017.
 */
public class QuestionClientSide extends QuestionClientSideAbstract {
    private String toTranslate;

    public QuestionClientSide(ArrayList<String> answers, String toTranslate) {
        super(answers);
        this.toTranslate = toTranslate;
    }

    public QuestionClientSide(QuestionClientSide q) {
        super();
        for (int i = 0; i < q.getAnswers().size(); i++) {
            answers.add(q.getAnswers().get(i));
        }
        toTranslate = q.getToTranslate();

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
        StringBuilder ret = new StringBuilder();
        ret.append("toTranslate: " + toTranslate + "\n");
        for (int i = 0; i < 4; i++) {
            ret.append("" + i + " " + answers.get(i) + "\n");
        }
        return ret.toString();
    }
}
