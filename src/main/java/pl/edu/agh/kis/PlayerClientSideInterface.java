package pl.edu.agh.kis;

/**
 * Created by Karl on 07.01.2017.
 */
public interface PlayerClientSideInterface {
    QuestionClientSide getQuestion();

    boolean sendReply();

    boolean quit();
}
