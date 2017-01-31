package pl.edu.agh.kis.utils;

import pl.edu.agh.kis.Model.question.QuestionServerSideAbstract;
import pl.edu.agh.kis.answer.Answer;

/**
 * Created by Karl on 13.01.2017.
 * Class knowing everything about answers - which answer is correct
 */
public class AnswerChecker {
    public boolean isTrue(QuestionServerSideAbstract question, Answer answer) {
        if (answer == null || answer.getReply() == null || answer.getReply().getPlayerChoice() == null) return false;
        if (question.getAnswers().get(0).equals(answer.getReply().getPlayerChoice())) {
            return true;
        }
        return false;
    }

}
