package pl.edu.agh.kis.Model;

/**
 * Created by Karl on 07.01.2017.
 */
public interface PlayerServerSideInterface {
    boolean sendQuestion(QuestionClientSide question);

    Answer answer();

    Reply getReply();

    boolean quit();

    void closeConnection();

    void addPoints(int numberOfPoints);
}
