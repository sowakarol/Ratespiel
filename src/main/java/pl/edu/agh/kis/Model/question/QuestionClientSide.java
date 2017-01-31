package pl.edu.agh.kis.Model.question;

import java.util.ArrayList;

/**
 * Created by Karl on 06.01.2017.
 * Question from client side
 */
public class QuestionClientSide extends QuestionClientSideAbstract {
    /**
     * string of a question like word to translate
     */
    private String toTranslate;

    /**
     * @param answers     all possible answers
     * @param toTranslate string of a question or word to translate
     */
    public QuestionClientSide(ArrayList<String> answers, String toTranslate) {
        super(answers);
        this.toTranslate = toTranslate;
    }

    /**
     * @param q question from client side
     */
    public QuestionClientSide(QuestionClientSide q) {
        super();
        for (int i = 0; i < q.getAnswers().size(); i++) {
            answers.add(q.getAnswers().get(i));
        }
        toTranslate = q.getToTranslate();

    }

    /**
     * @param question question from server side
     */
    public QuestionClientSide(QuestionServerSide question) {
        super(question.getAnswers());
        this.toTranslate = question.getToTranslate();
    }

    /**
     * @return toTranslate value
     */
    public String getToTranslate() {
        return toTranslate;
    }


    /**
     * @return String of this class - first question, then 4 possible answers
     */
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
