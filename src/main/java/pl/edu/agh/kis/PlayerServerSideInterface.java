package pl.edu.agh.kis;

/**
 * Created by Karl on 07.01.2017.
 */
public interface PlayerServerSideInterface {
    boolean sendQuestion(QuestionClientSide question);

    Answer answer();

    Reply getReply();

    boolean quit();

    void closeConnection();
}
