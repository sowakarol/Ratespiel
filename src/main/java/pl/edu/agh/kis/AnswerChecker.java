package pl.edu.agh.kis;

import pl.edu.agh.kis.Model.Answer;
import pl.edu.agh.kis.Model.QuestionServerSideAbstract;

/**
 * Created by Karl on 13.01.2017.
 */
public class AnswerChecker {
    boolean isTrue(QuestionServerSideAbstract question, Answer answer) {
        if (question.getAnswers().get(0).equals(answer.getReply().getPlayerChoice())) {
            return true;
        }
        return false;
    }

}
