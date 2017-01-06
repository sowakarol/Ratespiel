package pl.edu.agh.kis;

/**
 * Created by Karl on 06.01.2017.
 */
public interface PlayerClientSide {
    QuestionClientSide getQuestion();

    boolean sendReply();

    boolean quit();
}
