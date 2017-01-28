package pl.edu.agh.kis.utils;

import org.junit.Test;
import pl.edu.agh.kis.Model.Answer;
import pl.edu.agh.kis.Model.Reply;
import pl.edu.agh.kis.Model.question.QuestionServerSide;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Karl on 28.01.2017.
 */
public class AnswerCheckerTest {


    String path = RatespielGetPropertyValues.getPath();

    @Test
    public void checkCorrectAnswer() {
        QuestionServerSide questionServerSide = new QuestionServerSide(1, path);

        AnswerChecker answerChecker = new AnswerChecker();

        assertEquals(true, answerChecker.isTrue(questionServerSide,
                new Answer(new Reply(questionServerSide.getAnswers().get(0), Long.MAX_VALUE), 1)));
    }

    @Test
    public void checkFalseAnswer() {
        QuestionServerSide questionServerSide = new QuestionServerSide(1, path);

        AnswerChecker answerChecker = new AnswerChecker();

        assertEquals(false, answerChecker.isTrue(questionServerSide,
                new Answer(new Reply(questionServerSide.getAnswers().get(1), Long.MAX_VALUE), 1)));
    }

    @Test(expected = Exception.class)
    public void checkNullAnswer() {
        QuestionServerSide questionServerSide = new QuestionServerSide(-1, path);

        AnswerChecker answerChecker = new AnswerChecker();

        assertEquals(false, answerChecker.isTrue(questionServerSide,
                new Answer(new Reply(questionServerSide.getAnswers().get(0), Long.MAX_VALUE), 1)));
    }


    @Test(expected = Exception.class)
    public void checkNullQuestion() {
        QuestionServerSide questionServerSide = null;

        AnswerChecker answerChecker = new AnswerChecker();

        assertEquals(false, answerChecker.isTrue(questionServerSide,
                new Answer(new Reply(questionServerSide.getAnswers().get(0), Long.MAX_VALUE), 1)));
    }
}