package pl.edu.agh.kis.utils;

import pl.edu.agh.kis.Model.Answer;
import pl.edu.agh.kis.Model.question.QuestionServerSideAbstract;

/**
 * Created by Karl on 13.01.2017.
 */
public class AnswerChecker {
    public boolean isTrue(QuestionServerSideAbstract question, Answer answer) {
        if (question.getAnswers().get(0).equals(answer.getReply().getPlayerChoice())) {
            return true;
        }
        return false;
    }

}
